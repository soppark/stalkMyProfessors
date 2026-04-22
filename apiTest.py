from scholarly import scholarly


# so basically we can do this for every single prof by looking up their Author ID on google scholar
author = scholarly.search_author_id('PMVUPvIAAAAJ')
scholarly.pprint(scholarly.fill(author, sections=['basics', 'indices', 'publications']))