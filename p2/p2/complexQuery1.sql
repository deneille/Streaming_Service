--Part(9)
--Find a list of users who has watched more than 3 seasons of a tvshow available in their region. (With this list we can target these users' recommendation list for more long tv shows)
SELECT accountuser.username, media.title, available_in.regname, season.seasonnum
FROM available_in
INNER JOIN media ON (available_in.mid = media.mid)
INNER JOIN accountuser ON (available_in.regname = accountuser.regname)
INNER JOIN season ON (available_in.mid = season.mid)
WHERE available_in.regname is NOT NULL
AND media.title is NOT NULL
AND season.seasonnum > 2
GROUP BY available_in.regname, media.title, accountuser.username, season.seasonnum;
