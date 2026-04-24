import csv
import os
from scholarly import scholarly

DATA_DIR = "data"
os.makedirs(DATA_DIR, exist_ok=True)

with open("professors.csv", "r") as csvfile:
    reader = csv.reader(csvfile)
    for row in reader:
        author_id = row[0].strip()
        departments = row[1].strip()  # already semicolon-separated from CSV

        print(f"Fetching {author_id}...")
        try:
            author = scholarly.search_author_id(author_id)
            author = scholarly.fill(author, sections=['basics', 'indices', 'publications'])
        except Exception as e:
            print(f"  Error fetching {author_id}: {e}")
            continue

        filepath = os.path.join(DATA_DIR, f"{author_id}.txt")
        with open(filepath, "w", encoding="utf-8") as f:
            f.write(f"ID={author_id}\n")
            f.write(f"NAME={author.get('name', '')}\n")
            f.write(f"AFFILIATION={author.get('affiliation', '')}\n")
            f.write(f"DEPARTMENTS={departments}\n")

            email = author.get('email_domain', '')
            f.write(f"EMAIL={email}\n")

            website = author.get('homepage', '')
            f.write(f"WEBSITE={website}\n")

            interests = author.get('interests', [])
            f.write(f"INTERESTS={';'.join(interests)}\n")

            for pub in author.get('publications', []):
                bib = pub.get('bib', {})
                title = bib.get('title', '').replace('|', '-')
                year = bib.get('pub_year', '0')
                citation = bib.get('citation', '').replace('|', '-')
                f.write(f"PAPER={title}|{year}|{citation}\n")