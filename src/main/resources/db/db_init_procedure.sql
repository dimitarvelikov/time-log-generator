DELIMITER //
	DROP PROCEDURE IF EXISTS initializeDb;


    CREATE PROCEDURE initializeDb
    (
		IN  numUsers INT
    )
    BEGIN
	    #Common variables
		DECLARE counter, projectsSize, fnSize, lnSize,dmnSize INT DEFAULT 0;
        #Create user records variables
        DECLARE tempFname, tempLname VARCHAR(20) DEFAULT '';
        #Create time_log records variables
        DECLARE timeLogCounter, numTimeLogRecords INT DEFAULT 0;
        DECLARE amountWorkedHrs,genWorkHrs FLOAT DEFAULT 0;
		DECLARE logDate DATE;
        DECLARE currentDate DATE DEFAULT CURRENT_DATE();

        #disable foreign key check, truncate tables
        SET FOREIGN_KEY_CHECKS = 0;
        TRUNCATE TABLE time_log;
        TRUNCATE TABLE user;
        TRUNCATE TABLE project;
        SET FOREIGN_KEY_CHECKS = 1;

		SET @firstNames='["John","Gringo","Mark","Lisa","Maria","Sonya","Philip","Jose","Lorenzo","George","Justin"]';
        SET @lastNames = '["Johnson","Lamas","Jackson","Brown","Mason","Rodriguez","Roberts","Thomas","Rose","McDonalds"]';
		SET @domains = '["hotmail.com","gmail.com","live.com"]';
        SET @projects =  '["My own","Outcons","Free Time"]';

        SET fnSize = JSON_LENGTH(@firstNames);
        SET lnSize = JSON_LENGTH(@lastNames);
        SET dmnSize = JSON_LENGTH(@domains);
        SET projectsSize = JSON_LENGTH(@projects);

        #Insert Project rows
		SET @projectsQuery= 'INSERT INTO outcons.project (name) VALUES ';
		WHILE counter < projectsSize DO
			SET @projectsQuery = CONCAT(@projectsQuery, '(\'',JSON_EXTRACT(@projects, CONCAT('$[',counter,']')),'\'),');
			SET counter = counter + 1;
        END WHILE;
        SET @projectsQuery =    CONCAT(SUBSTRING_INDEX(REPLACE(@projectsQuery,'"',''),',',3),';');
		PREPARE insertProjectsStatement FROM @projectsQuery;
    	EXECUTE insertProjectsStatement;
        SET counter =0;


        SET @projectsQuery = 'INSERT INTO outcons.user (name,surname,email) VALUES ';
        #Insert User rows
		WHILE counter < numUsers DO
			SET counter = counter + 1;
			SET tempFname = JSON_EXTRACT(@firstNames, CONCAT('$[',FLOOR(RAND()*(fnSize)),']'));
			SET tempLname = JSON_EXTRACT(@lastNames, CONCAT('$[',FLOOR(RAND()*(lnSize)),']'));
			SET @projectsQuery = CONCAT(@projectsQuery, '(\'',tempFname,'\',\'',tempLname,'\',\'',tempFname,'.',tempLname,'@',JSON_EXTRACT(@domains,CONCAT( '$[',FLOOR(RAND()*(dmnSize)),']')) ,'\'),');
		 END WHILE;

		 SET @projectsQuery =  CONCAT( REPLACE(SUBSTRING(@projectsQuery, 1, CHAR_LENGTH(@projectsQuery)-1),'"',''),';');
		 PREPARE insertUsersStmnt FROM @projectsQuery;
    	 EXECUTE insertUsersStmnt;
		 SET counter =0;


		#Insert Time Log rows
        WHILE counter < numUsers DO
			# in the inner while - counter variable will be used also as user_id
			SET counter = counter + 1;
            # random number to determine how many records will be created for this user (1-20)
            SET numTimeLogRecords = FLOOR(RAND()*(20)+1);
            SET timeLogCounter = 0;

            WHILE timeLogCounter < numtimeLogRecords DO
				SET timeLogCounter = timeLogCounter + 1;
                SET logDate = DATE_SUB(currentDate, INTERVAL FLOOR(RAND()*60) DAY);
				SELECT SUM(time_log.time) FROM outcons.time_log WHERE user_id = counter AND date = logDate INTO amountWorkedHrs;
				SET genWorkHrs = FLOOR(RAND()*(800 - 25 + 1) + 25)/100; #(from 25 to 800) / 100
                if(amountWorkedHrs IS NULL) THEN
					INSERT INTO time_log (date, time,project_id,user_id) VALUES
							(
								logDate,
								genWorkHrs,
								FLOOR(RAND()*(3) + 1),
								counter
							);
				ELSEIF amountWorkedHrs + genWorkHrs <= 8 THEN
					INSERT INTO time_log (date, time,project_id,user_id) VALUES
							(
								logDate,
								genWorkHrs,
								FLOOR(RAND()*(3) + 1),
								counter
							);
                END IF;
            END WHILE;
        END WHILE;
	END //

DELIMITER ;
