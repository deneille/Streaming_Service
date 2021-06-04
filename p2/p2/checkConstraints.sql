-- Set up
INSERT INTO Account VALUES (1);
INSERT INTO Region VALUES ('Canada');
INSERT INTO AccountUser VALUES ('Basta', 1, 'Canada');
INSERT INTO Media VALUES (1, 'Breaking Bad', NULL, TRUE);
INSERT INTO Season VALUES (1, 1);

-- Insert broken entries
-- value (last column) must be between 1-5
INSERT INTO Rating VALUES (1, 'Basta', 1, 0);
-- seasonNum (first column) must be positive
INSERT INTO Season VALUES (0, 1);
-- epNum (first column) must be positive
INSERT INTO Episode Values (0, 1, 1, 'Title');

-- Insert proper entries
INSERT INTO Rating VALUES (1, 'Basta', 1, 4);
INSERT INTO Season VALUES (2, 1);
INSERT INTO Episode Values (1, 1, 1, 'Title');

-- Modify proper entries with broken entries
UPDATE Rating SET value = 0 WHERE mid = 1 AND userName = 'Basta' AND accid = 1;
UPDATE Season SET seasonNum = 0 WHERE seasonNum = 1 AND mid = 1;
UPDATE Episode SET epNum = 0 WHERE epNum =1 AND seasonNum = 1 and mid = 1;
