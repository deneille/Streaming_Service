--Part 9
--Find the average ratings by the title of media and geographic region for users. (This data will be used to determine if to stream a tv show or movie in a region).
SELECT available_in.regname, media.title, AVG(rating.value) AS average_rating
FROM media
INNER JOIN available_in ON (media.mid = available_in.mid)
INNER JOIN rating ON (media.mid = rating.mid)
WHERE available_in.regname is NOT NULL
AND media.title is NOT NULL
GROUP BY available_in.regname, media.title;
