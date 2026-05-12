"""
add_coauthors.py

Reads the master CSV produced by parse.py (default: data/all_output.csv) and
adds randomly generated coauthor columns after each paper's venue field.
Coauthors are sampled from the set of other professors in the same dataset
(so they are real names that appear in the file), never including the paper's
own author.

Usage:
    python add_coauthors.py
    python add_coauthors.py --input data/all_output.csv --output data/all_output_with_coauthors.csv
    python add_coauthors.py --min 1 --max 4 --seed 42
"""

from __future__ import annotations

import argparse
import csv
import os
import random
import re


NAME_RE = re.compile(r"^[A-Z][A-Za-zÀ-ÖØ-öø-ÿ.'() -]+$")


def add_coauthors(
    input_path: str,
    output_path: str,
    min_coauthors: int = 0,
    max_coauthors: int = 1,
    seed: int | None = None,
) -> list[dict[str, str]]:
    if seed is not None:
        random.seed(seed)

    with open(input_path, newline="", encoding="utf-8") as infile:
        reader = csv.DictReader(infile)
        if reader.fieldnames is None:
            raise ValueError(f"{input_path} is empty or missing a header row")
        input_columns = reader.fieldnames
        rows = list(reader)

    if "name" not in input_columns or "title" not in input_columns:
        raise ValueError("Input CSV must contain at least 'name' and 'title' columns")

    # Pool of every professor name that appears in valid professor rows.
    all_profs = sorted(
        {row["name"].strip() for row in rows if is_professor_row(row)}
    )

    # Number of new columns is bounded by the max we ever generate.
    coauthor_cols = [f"coauthor_{i + 1}" for i in range(max_coauthors)]
    output_columns = place_coauthor_columns(input_columns, coauthor_cols)

    for row in rows:
        author = row.get("name", "").strip()
        title = row.get("title", "").strip()

        for col in coauthor_cols:
            row[col] = ""

        # Rows without a paper title or with shifted identity fields get no
        # generated coauthors.
        if not title or not is_professor_row(row):
            continue

        candidates = [p for p in all_profs if p != author]
        if not candidates:
            continue

        k = random.randint(min_coauthors, min(max_coauthors, len(candidates)))
        picks = random.sample(candidates, k)

        for i, name in enumerate(picks):
            row[coauthor_cols[i]] = name

    os.makedirs(os.path.dirname(os.path.abspath(output_path)), exist_ok=True)
    with open(output_path, "w", newline="", encoding="utf-8") as outfile:
        writer = csv.DictWriter(outfile, fieldnames=output_columns, extrasaction="ignore")
        writer.writeheader()
        writer.writerows(rows)

    print(f"Wrote {len(rows)} rows with up to {max_coauthors} coauthors -> {output_path}")
    return rows


def place_coauthor_columns(input_columns: list[str], coauthor_cols: list[str]) -> list[str]:
    """Insert coauthor columns immediately after venue when possible."""
    cleaned_columns = [col for col in input_columns if not col.startswith("coauthor_")]
    insert_after = "venue" if "venue" in cleaned_columns else cleaned_columns[-1]
    insert_at = cleaned_columns.index(insert_after) + 1
    return cleaned_columns[:insert_at] + coauthor_cols + cleaned_columns[insert_at:]


def is_professor_row(row: dict[str, str]) -> bool:
    """Return true when a row's identity fields look like a real professor."""
    name = row.get("name", "").strip()
    email = row.get("email", "").strip()
    affiliation = row.get("affiliation", "").strip()
    department = row.get("departments", "").strip()

    return (
        bool(NAME_RE.fullmatch(name))
        and email.endswith("pomona.edu")
        and affiliation != "@pomona.edu"
        and not department.startswith("http")
    )


def main():
    here = os.path.dirname(os.path.abspath(__file__))
    default_in = os.path.join(here, "data", "all_output.csv")
    default_out = os.path.join(here, "data", "all_output_with_coauthors.csv")

    ap = argparse.ArgumentParser(description=__doc__)
    ap.add_argument("--input", default=default_in, help="Input CSV path")
    ap.add_argument("--output", default=default_out, help="Output CSV path")
    ap.add_argument("--min", type=int, default=0, dest="min_coauthors",
                    help="Minimum coauthors per paper (default: 0)")
    ap.add_argument("--max", type=int, default=1, dest="max_coauthors",
                    help="Maximum coauthors per paper (default: 1)")
    ap.add_argument("--seed", type=int, default=None,
                    help="Random seed for reproducibility (optional)")
    args = ap.parse_args()

    if args.min_coauthors < 0 or args.max_coauthors < args.min_coauthors:
        raise SystemExit("--min must be >= 0 and --max must be >= --min")

    add_coauthors(
        input_path=args.input,
        output_path=args.output,
        min_coauthors=args.min_coauthors,
        max_coauthors=args.max_coauthors,
        seed=args.seed,
    )


if __name__ == "__main__":
    main()
