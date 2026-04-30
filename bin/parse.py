import pandas as pd
import os

def parse_single_txt(input_file):
    rows = []

    current = {
        "name": "",
        "affiliation": "",
        "departments": "",
        "email": "",
        "website": "",
        "interests": "",
        "papers": []
    }

    def save_current():
        if current["name"] == "":
            return

        if len(current["papers"]) == 0:
            rows.append({
                "name": current["name"],
                "affiliation": current["affiliation"],
                "departments": current["departments"],
                "email": current["email"],
                "website": current["website"],
                "interests": current["interests"],
                "title": "",
                "year": "",
                "venue": ""
            })
        else:
            for p in current["papers"]:
                parts = p.split("|")
                title = parts[0] if len(parts) > 0 else ""
                year = parts[1] if len(parts) > 1 else ""
                venue = parts[2] if len(parts) > 2 else ""

                rows.append({
                    "name": current["name"],
                    "affiliation": current["affiliation"],
                    "departments": current["departments"],
                    "email": current["email"],
                    "website": current["website"],
                    "interests": current["interests"],
                    "title": title,
                    "year": year,
                    "venue": venue
                })

    with open(input_file, "r", encoding="utf-8") as f:
        for line in f:
            line = line.strip()
            if not line:
                continue

            if line.startswith("NAME="):
                save_current()
                current["name"] = line.replace("NAME=", "").strip()
                current["affiliation"] = ""
                current["departments"] = ""
                current["email"] = ""
                current["website"] = ""
                current["interests"] = ""
                current["papers"] = []

            elif line.startswith("AFFILIATION="):
                current["affiliation"] = line.replace("AFFILIATION=", "").strip()

            elif line.startswith("DEPARTMENTS="):
                current["departments"] = line.replace("DEPARTMENTS=", "").strip()

            elif line.startswith("EMAIL="):
                current["email"] = line.replace("EMAIL=", "").strip()

            elif line.startswith("WEBSITE="):
                current["website"] = line.replace("WEBSITE=", "").strip()

            elif line.startswith("INTERESTS="):
                current["interests"] = line.replace("INTERESTS=", "").strip()

            elif line.startswith("PAPER="):
                current["papers"].append(line.replace("PAPER=", "").strip())

        save_current()

    return rows


def build_master_csv(id_list_file, input_folder="data", output_file="data/all_output.csv"):
    all_rows = []

    with open(id_list_file, "r", encoding="utf-8") as f:
        for line in f:
            line = line.strip()
            if not line:
                continue

            file_id, dept = line.split(",")

            txt_path = os.path.join(input_folder, f"{file_id}.txt")

            if not os.path.exists(txt_path):
                print(f"Missing file: {txt_path}")
                continue

            rows = parse_single_txt(txt_path)

            all_rows.extend(rows)

    df = pd.DataFrame(all_rows)
    df.to_csv(output_file, index=False)

    print(f"Saved master CSV to {output_file}")


# usage
build_master_csv("professors.csv")