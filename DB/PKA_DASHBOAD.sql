set pages 25000
set line 7777
set trimspool on
set echo on
set define off
set long 99999
set colsep |

spool 20180409_PKA_DASHBOAD_OLD.log

CREATE OR REPLACE PACKAGE BODY "ORIG_APP"."PKA_DASHBOARD" as
FUNCTION F_CHECK_ROLE (V_ROLE_CHECK VARCHAR2,
                       V_ROLE_LIST VARCHAR)
    RETURN NUMBER AS
    V_FOUND NUMBER;
    V_CHECK VARCHAR2(30);
    I NUMBER := 0;
    V_LENGTH_CHECK NUMBER := LENGTH(V_ROLE_LIST);
    V_LAST_FOUND NUMBER := 0;
    V_COUNT NUMBER := 0;
BEGIN
    V_FOUND := INSTR(V_ROLE_LIST,',',1) ;

    IF V_FOUND > 0 THEN

        WHILE I < V_LENGTH_CHECK
        LOOP
            V_COUNT := V_COUNT + 1;
            V_FOUND := INSTR(V_ROLE_LIST,',',I+1) ;
            IF V_FOUND > 0 THEN
                V_CHECK := SUBSTR(V_ROLE_LIST,I,V_FOUND -V_LAST_FOUND - 1);

                IF V_CHECK = V_ROLE_CHECK THEN
                    RETURN 1;
                END IF;

                I := V_FOUND + 1;
                V_LAST_FOUND := V_FOUND;
            ELSE
                V_CHECK := SUBSTR(V_ROLE_LIST,I);
                IF V_CHECK = V_ROLE_CHECK THEN
                    RETURN 1;
                ELSE
                    RETURN 0;
                END IF;
            END IF;

        END LOOP;
    ELSE
        IF V_ROLE_CHECK = V_ROLE_LIST THEN
            RETURN 1;
        ELSE
            RETURN 0;
        END IF;
    END IF;

    RETURN 0;
END;

function f_return_process_day (v_date1 date,v_date2 date,v_start varchar,v_end varchar)
RETURN NUMBER
IS
--------------------------------------------------
-- Author: Andrew Higginson (http://drewzh.com) --
--------------------------------------------------
    START_DTM DATE;
    END_DTM DATE;
    START_HOUR NUMBER;
    END_HOUR   NUMBER;
    START_MINS NUMBER;
    END_MINS   NUMBER;
    DEBUG_MODE BOOLEAN;
    BUSINESS_OPEN DATE;
    BUSINESS_CLOSE DATE;
    BUSINESS_TIME DATE;
    WORKING_DAYS  NUMBER;
    WORKING_MINUTES NUMBER;
BEGIN
    -- DEFINE CONSTANTS
    DEBUG_MODE     := FALSE;
    BUSINESS_OPEN  := TO_DATE(V_START, 'HH24:MI');
    BUSINESS_CLOSE := TO_DATE(V_END, 'HH24:MI');

    -- INITIALISE VARIABLES
    START_DTM  := V_DATE1;
    END_DTM    := V_DATE2;
    START_HOUR := TO_NUMBER (TO_CHAR (START_DTM, 'HH24'));
    END_HOUR   := TO_NUMBER (TO_CHAR (END_DTM, 'HH24'));
    START_MINS := TO_NUMBER (TO_CHAR (START_DTM, 'MI'));
    END_MINS   := TO_NUMBER (TO_CHAR (END_DTM, 'MI'));

    -- VARIABLE FOR STORING FINAL TIME DIFFERENCE
    BUSINESS_TIME := TO_DATE('00:00', 'HH24:MI');

    -- RETURN ON MALFORMED DATES
    IF(START_DTM > END_DTM) THEN
        IF (DEBUG_MODE) THEN
            DBMS_OUTPUT.PUT_LINE('Start date cannot be greater than the end date');
        END IF;
        RETURN 0;
    END IF;

    -- CHECK THAT START TIME IS BEFORE START OF BUSINESS DAY
    IF(TO_DATE(TO_CHAR(START_DTM, 'HH24:MI'), 'HH24:MI') < BUSINESS_OPEN) THEN
        START_DTM                                         := START_DTM + (BUSINESS_OPEN - TO_DATE(TO_CHAR(START_DTM, 'HH24:MI'), 'HH24:MI'));
        START_HOUR                                        := TO_NUMBER (TO_CHAR (START_DTM, 'HH24'));
        START_MINS                                        := TO_NUMBER (TO_CHAR (START_DTM, 'MI'));
        IF (DEBUG_MODE) THEN
            DBMS_OUTPUT.PUT_LINE('Start time is before start of business day, changed to: ' || start_dtm);
        END IF;
    END IF;

    -- CHECK THAT END TIME IS BEFORE START OF BUSINESS DAY
    IF(TO_DATE(TO_CHAR(END_DTM, 'HH24:MI'), 'HH24:MI') < BUSINESS_OPEN) THEN
        END_DTM                                         := END_DTM + (BUSINESS_OPEN - TO_DATE(TO_CHAR(END_DTM, 'HH24:MI'), 'HH24:MI'));
        END_HOUR                                        := TO_NUMBER (TO_CHAR (END_DTM, 'HH24'));
        END_MINS                                        := TO_NUMBER (TO_CHAR (END_DTM, 'MI'));
        IF (DEBUG_MODE) THEN
            DBMS_OUTPUT.PUT_LINE('End time is before start of business day, changed to: ' || end_dtm);
        END IF;
    END IF;

    -- CHECK THAT START TIME IS AFTER END OF BUSINESS DAY
    IF(TO_DATE(TO_CHAR(START_DTM, 'HH24:MI'), 'HH24:MI') > BUSINESS_CLOSE) THEN
        START_DTM                                         := START_DTM + (BUSINESS_CLOSE - TO_DATE(TO_CHAR(START_DTM, 'HH24:MI'), 'HH24:MI'));
        START_HOUR                                        := TO_NUMBER (TO_CHAR (START_DTM, 'HH24'));
        START_MINS                                        := TO_NUMBER (TO_CHAR (START_DTM, 'MI'));
        IF (DEBUG_MODE) THEN
            DBMS_OUTPUT.PUT_LINE('Start time is after end of business day, changed to: ' || start_dtm);
        END IF;
    END IF;

    -- CHECK THAT END TIME IS AFTER END OF BUSINESS DAY
    IF(TO_DATE(TO_CHAR(END_DTM, 'HH24:MI'), 'HH24:MI') > BUSINESS_CLOSE) THEN
        END_DTM                                         := END_DTM + (BUSINESS_CLOSE - TO_DATE(TO_CHAR(END_DTM, 'HH24:MI'), 'HH24:MI'));
        END_HOUR                                        := TO_NUMBER (TO_CHAR (END_DTM, 'HH24'));
        END_MINS                                        := TO_NUMBER (TO_CHAR (END_DTM, 'MI'));
        IF (DEBUG_MODE) THEN
            DBMS_OUTPUT.PUT_LINE('End time is after end of business day, changed to: ' || end_dtm);
        END IF;
    END IF;

    -- CALCULATE FULL WORKING DAYS BETWEEN DATES
    SELECT
        COUNT(*)
    INTO
        WORKING_DAYS
    FROM
        (
            SELECT
                ROWNUM R
            FROM
                ALL_OBJECTS
            WHERE
                ROWNUM <=
                (
                    SELECT
                        TRUNC(END_DTM) - TRUNC(START_DTM)
                    FROM
                        DUAL ) )
        -- EXCLUDE WEEKENDS
    WHERE
        TRIM(TO_CHAR(TRUNC(START_DTM +R -1),'DAY')) NOT IN ('SATURDAY',
                                                            'SUNDAY')
        -- EXLUDE ANY PUBLIC HOLIDAYS
    AND NOT EXISTS
        (
            SELECT
                NULL
            FROM
                MS_HOLIDAY M
            WHERE
                M.HOLIDAY_DATE = TRUNC(START_DTM +R -1) );

    -- CALCULATE TIME DIFFERENCE IN MINUTES
    WORKING_MINUTES := (TO_DATE(TO_CHAR(END_DTM, 'HH24:MI'), 'HH24:MI') - TO_DATE(TO_CHAR(START_DTM, 'HH24:MI'), 'HH24:MI'))*24*60;

    IF (WORKING_DAYS <= 0) THEN
        BUSINESS_TIME := BUSINESS_TIME + (WORKING_MINUTES/60/24);
    END IF;
    IF (WORKING_DAYS > 0) THEN
        BUSINESS_TIME := BUSINESS_TIME + ((((BUSINESS_CLOSE - BUSINESS_OPEN)*24) * WORKING_DAYS)/24) + (WORKING_MINUTES/60/24);
    END IF;

    IF (debug_mode) THEN
        DBMS_OUTPUT.PUT_LINE(working_days || ' days multiplied by ' || (BUSINESS_CLOSE - BUSINESS_OPEN)*24 || ' business hours plus ' || working_minutes || ' extra minutes');
        DBMS_OUTPUT.PUT_LINE('Calculated ' || ROUND((business_time-TO_DATE('00:00', 'HH24:Mi'))*24*60) || ' minutes between ' || START_DTM || ' and ' || END_DTM);
    END IF;

    -- RETURN WORKING TIME IN MINUTES
    RETURN ROUND((BUSINESS_TIME-TO_DATE('00:00', 'HH24:MI'))*24*60);
END;

FUNCTION F_CONV_NUM_TO_CHAR(V_NUMBER NUMBER) RETURN VARCHAR2 AS
    V_DAY NUMBER;
    V_HOUR NUMBER;
    V_MIN NUMBER;
    V_RESULT VARCHAR2(100);
BEGIN
    V_DAY := TRUNC(V_NUMBER);
    V_HOUR := TRUNC( MOD(V_NUMBER,1)* 24);
    V_MIN :=  MOD(MOD(V_NUMBER,1)* 1440,60);
    V_RESULT := V_DAY || ' Day ' || LPAD(V_HOUR,2,'0') || ':' || LPAD(TRUNC(V_MIN),2,'0');
    RETURN V_RESULT;
END;

FUNCTION F_GET_MON(V_DATE DATE) RETURN VARCHAR2
AS
    V_RESULT VARCHAR2(10);
BEGIN
    CASE (TO_CHAR(V_DATE,'MM'))
      WHEN '01' THEN
        V_RESULT := 'Jan';
      WHEN '02' THEN
        V_RESULT := 'Feb';
      WHEN '03' THEN
        V_RESULT := 'Mar';
      WHEN '04' THEN
        V_RESULT := 'Apr';
      WHEN '05' THEN
        V_RESULT := 'May';
      WHEN '06' THEN
        V_RESULT := 'Jun';
      WHEN '07' THEN
        V_RESULT := 'Jul';
      WHEN '08' THEN
        V_RESULT := 'Aug';
      WHEN '09' THEN
        V_RESULT := 'Sep';
      WHEN '10' THEN
        V_RESULT := 'Oct';
      WHEN '11' THEN
        V_RESULT := 'Nov';
      WHEN '12' THEN
        V_RESULT := 'Dec';
      END CASE;

    RETURN V_RESULT;
END;

--This function is private, returns private type and is used within this package only!
FUNCTION F_COUNT_WIP_BY_ROLE(V_ROLE VARCHAR2, V_PREVIOUS_ROLE VARCHAR2) RETURN T_STATION_APP
AS
    V_RESULT T_STATION_APP;
BEGIN
    --Improve performance, Neglect table lock for SELECT-FOR-INSERT
    COMMIT;
    SET TRANSACTION READ ONLY;

    IF V_PREVIOUS_ROLE IS NOT NULL THEN

        SELECT COUNT (DISTINCT CASE WHEN OG.PRIORITY IN (3, 4) THEN og.APPLICATION_GROUP_ID ELSE NULL END) WIP_NORMAL,
               COUNT (DISTINCT CASE WHEN OG.PRIORITY IN (1, 2) THEN og.APPLICATION_GROUP_ID ELSE NULL END) WIP_URGENT
          INTO V_RESULT.WIP_NORMAL, V_RESULT.WIP_URGENT
          FROM ORIG_APPLICATION_GROUP OG JOIN DHB_JOB_STATE T ON T.JOB_STATE = OG.JOB_STATE AND T.PARAM_CODE = 'WIP_JOBSTATE_' || V_ROLE
          JOIN DHB_JOB_STATE TP ON OG.PREV_JOB_STATE = TP.JOB_STATE AND TP.PARAM_CODE = 'WIP_JOBSTATE_' || V_PREVIOUS_ROLE;

    ELSE

        SELECT COUNT (DISTINCT CASE WHEN OG.PRIORITY IN (3, 4) THEN og.APPLICATION_GROUP_ID ELSE NULL END) WIP_NORMAL,
               COUNT (DISTINCT CASE WHEN OG.PRIORITY IN (1, 2) THEN og.APPLICATION_GROUP_ID ELSE NULL END) WIP_URGENT
          INTO V_RESULT.WIP_NORMAL, V_RESULT.WIP_URGENT
          FROM ORIG_APPLICATION_GROUP OG JOIN DHB_JOB_STATE T ON T.JOB_STATE = OG.JOB_STATE AND T.PARAM_CODE = 'WIP_JOBSTATE_' || V_ROLE;

    END IF;


    --Reset transaction mode to default
    COMMIT;
    RETURN V_RESULT;
END;


--This function is private, returns private type and is used within this package only!
FUNCTION F_COUNT_INCOMING_APP RETURN T_INCOMING_APP
AS
V_IA_NORMAL_WIP NUMBER;
V_IA_URGENT_WIP NUMBER;
V_DE1_1_NORMAL_WIP NUMBER;
V_DE1_1_URGENT_WIP NUMBER;
V_DE1_2_NORMAL_WIP NUMBER;
V_DE1_2_URGENT_WIP NUMBER;
V_DV_NORMAL_WIP NUMBER;
V_DV_URGENT_WIP NUMBER;
V_VT_NORMAL_WIP NUMBER;
V_VT_URGENT_WIP NUMBER;
V_CA_NORMAL_WIP NUMBER;
V_CA_URGENT_WIP NUMBER;
V_DE2_NORMAL_WIP NUMBER;
V_DE2_URGENT_WIP NUMBER;
V_FU_NORMAL_WIP NUMBER;
V_FU_URGENT_WIP NUMBER;
V_FU_IA_NORMAL_WIP NUMBER;
V_FU_IA_URGENT_WIP NUMBER;
V_FU_DE1_1_NORMAL_WIP NUMBER;
V_FU_DE1_1_URGENT_WIP NUMBER;
V_STATION_APP T_STATION_APP;
V_RESULT T_INCOMING_APP;
BEGIN
    COMMIT;
    SET TRANSACTION READ ONLY;

    BEGIN
        FOR REC IN (SELECT DESCRIPTION1 ROLE, DESCRIPTION2 TYPE, VALUE1 VALUE
           FROM DHB_SUMMARY_DATA SS
           WHERE     SS.DHB_TYPE = '09'
                AND STATUS = 'W'                                                                                                                    --PICK CURRENT INDEX MONTH
                AND DHB_OWNER = (     SELECT DHB_OWNER
                                        FROM DHB_SUMMARY_DATA
                                       WHERE DHB_TYPE = '09'
                                 FETCH FIRST 1 ROW ONLY))
        LOOP
            IF REC.TYPE = 'urgent' THEN
                 IF REC.ROLE = 'IA' THEN
                    V_IA_URGENT_WIP := NVL(REC.VALUE,0);
                 ELSIF REC.ROLE = 'DE1.1' THEN
                    V_DE1_1_URGENT_WIP := NVL(REC.VALUE,0);
                 ELSIF REC.ROLE = 'DE1.2' THEN
                    V_DE1_2_URGENT_WIP := NVL(REC.VALUE,0);
                 ELSIF REC.ROLE = 'DV' THEN
                    V_DV_URGENT_WIP := NVL(REC.VALUE,0);
                 ELSIF REC.ROLE = 'VT' THEN
                    V_VT_URGENT_WIP := NVL(REC.VALUE,0);
                 ELSIF REC.ROLE = 'CA' THEN
                    V_CA_URGENT_WIP := NVL(REC.VALUE,0);
                 ELSIF REC.ROLE = 'DE2' THEN
                    V_DE2_URGENT_WIP := NVL(REC.VALUE,0);
                 ELSIF REC.ROLE = 'FU' THEN
                    V_FU_URGENT_WIP := NVL(REC.VALUE,0);
                 END IF;
             ELSE
                 IF REC.ROLE = 'IA' THEN
                    V_IA_NORMAL_WIP := NVL(REC.VALUE,0);
                ELSIF REC.ROLE = 'DE1.1' THEN
                    V_DE1_1_NORMAL_WIP := NVL(REC.VALUE,0);
                ELSIF REC.ROLE = 'DE1.2' THEN
                    V_DE1_2_NORMAL_WIP := NVL(REC.VALUE,0);
                ELSIF REC.ROLE = 'DV' THEN
                    V_DV_NORMAL_WIP := NVL(REC.VALUE,0);
                ELSIF REC.ROLE = 'VT' THEN
                    V_VT_NORMAL_WIP := NVL(REC.VALUE,0);
                ELSIF REC.ROLE = 'CA' THEN
                    V_CA_NORMAL_WIP := NVL(REC.VALUE,0);
                ELSIF REC.ROLE = 'DE2' THEN
                    V_DE2_NORMAL_WIP := NVL(REC.VALUE,0);
                ELSIF REC.ROLE = 'FU' THEN
                    V_FU_NORMAL_WIP := NVL(REC.VALUE,0);
                END IF;
            END IF;
        END LOOP;
        V_STATION_APP := F_COUNT_WIP_BY_ROLE('FU','IA');
        V_FU_IA_NORMAL_WIP := V_STATION_APP.WIP_NORMAL;
        V_FU_IA_URGENT_WIP := V_STATION_APP.WIP_URGENT;

        V_STATION_APP := F_COUNT_WIP_BY_ROLE('FU','DE1_1');
        V_FU_DE1_1_NORMAL_WIP := V_STATION_APP.WIP_NORMAL;
        V_FU_DE1_1_URGENT_WIP := V_STATION_APP.WIP_URGENT;

        --Set result
        V_RESULT.DE1_1_NORMAL := V_IA_NORMAL_WIP + V_FU_IA_NORMAL_WIP + V_FU_DE1_1_NORMAL_WIP;
        V_RESULT.DE1_1_URGENT := V_IA_URGENT_WIP + V_FU_IA_URGENT_WIP + V_FU_DE1_1_URGENT_WIP;
        V_RESULT.DE1_2_NORMAL := V_IA_NORMAL_WIP + V_DE1_1_NORMAL_WIP + V_FU_NORMAL_WIP;
        V_RESULT.DE1_2_URGENT := V_IA_URGENT_WIP + V_DE1_1_URGENT_WIP + V_FU_URGENT_WIP;
        V_RESULT.DV_NORMAL := V_IA_NORMAL_WIP + V_DE1_1_NORMAL_WIP + V_DE1_2_NORMAL_WIP + V_FU_NORMAL_WIP;
        V_RESULT.DV_URGENT := V_IA_URGENT_WIP + V_DE1_1_URGENT_WIP + V_DE1_2_URGENT_WIP + V_FU_URGENT_WIP;

        --Count refer and veto app
        BEGIN
            SELECT COUNT (CASE WHEN OG.PRIORITY IN (3,4) THEN og.APPLICATION_GROUP_ID ELSE NULL END) WIP_NORMAL,
                   COUNT (CASE WHEN OG.PRIORITY IN (1,2) THEN og.APPLICATION_GROUP_ID ELSE NULL END) WIP_URGENT
                  INTO V_RESULT.VT_NORMAL, V_RESULT.VT_URGENT
                  FROM ORIG_APPLICATION_GROUP OG JOIN DHB_JOB_STATE T ON T.JOB_STATE = OG.JOB_STATE
                  WHERE OG.JOB_STATE IN (SELECT JOB_STATE FROM DHB_JOB_STATE WHERE PARAM_CODE IN ('WIP_JOBSTATE_IA','WIP_JOBSTATE_DE1_1','WIP_JOBSTATE_DE1_2','WIP_JOBSTATE_DV'))
                  AND OG.COVERPAGE_TYPE = 'VETO' AND OG.LAST_DECISION = 'RE';
        EXCEPTION WHEN OTHERS THEN
            V_RESULT.VT_NORMAL := 0;
            V_RESULT.VT_URGENT := 0;
            DBMS_OUTPUT.PUT_LINE('UNABLE TO COUNT VT INCOMING APP, '||SQLERRM);
        END;

        V_RESULT.CA_NORMAL := V_RESULT.VT_NORMAL + V_VT_NORMAL_WIP;--V_RESULT.DV_NORMAL + V_DV_NORMAL_WIP + V_RESULT.VT_NORMAL + V_VT_NORMAL_WIP;
        V_RESULT.CA_URGENT := V_RESULT.VT_URGENT + V_VT_URGENT_WIP;--V_RESULT.DV_URGENT + V_DV_URGENT_WIP + V_RESULT.VT_URGENT + V_VT_URGENT_WIP;
        V_RESULT.DE2_NORMAL := V_RESULT.CA_NORMAL + V_CA_NORMAL_WIP;
        V_RESULT.DE2_URGENT := V_RESULT.CA_URGENT + V_CA_URGENT_WIP;

    EXCEPTION WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('UNABLE TO COUNT INCOMING APP, '||SQLERRM);
    END;

    COMMIT;--RESET TRANSACTION
    RETURN V_RESULT;
END;

FUNCTION F_GET_SUBORDINATE_ROLE(V_USER_NAME VARCHAR2) RETURN VARCHAR2
AS
    V_RESULT VARCHAR2(100);
BEGIN
    BEGIN
    SELECT ROLE_NAME INTO V_RESULT
      FROM (    SELECT UT.*, LEVEL
                  FROM MS_USER_TEAM UT JOIN MS_TEAM T ON UT.TEAM_ID = T.TEAM_ID
            START WITH UPPER (UT.USER_ID) = UPPER (V_USER_NAME) --SUPERVISOR USER_NAME
            CONNECT BY PRIOR T.TEAM_ID = T.UNDER) T
            JOIN MS_USER_TEAM UT ON T.TEAM_ID = UT.TEAM_ID AND T.POSITION_ID = '4' --SINCE POSITION 4 AND 5 ARE IN THE SAME TEAM
           JOIN USER_ROLE UR ON UT.USER_ID = UR.USER_NAME
           JOIN ROLE R ON UR.ROLE_ID = R.ROLE_ID
     WHERE UT.POSITION_ID = 5 AND R.ROLE_NAME IN ('DE1_1','DE1_2','DV', 'VT','CA','DE2', 'FU') AND ROWNUM = 1;
     EXCEPTION WHEN OTHERS THEN
        NULL;
     END;
     RETURN V_RESULT;
END;


procedure p_gen_staff_perf_dashboard as
V_WORKING_HR_END_KBANK varchar2(5);
V_WORKING_HR_END_PHR varchar2(5);
V_WORKING_HR_START_KBANK varchar2(5);
V_WORKING_HR_START_PHR varchar2(5);
v_work_day_kbank number;
v_work_day_phr number;
v_application_date date;
begin
select trunc(app_date)   + (sysdate - trunc(sysdate))  into v_application_date from application_date;

/* no need to delete data in procedure*/
delete from DHB_STAFF_PERFORMANCE;

select value1 into V_WORKING_HR_END_KBANK from workflow_param where param_code = 'WORKING_HR_END_KBANK';
select value1 into V_WORKING_HR_END_PHR  from workflow_param where param_code = 'WORKING_HR_END_PHR';
select value1 into V_WORKING_HR_START_KBANK from workflow_param where param_code = 'WORKING_HR_START_KBANK';
select value1 into V_WORKING_HR_START_PHR  from workflow_param where param_code = 'WORKING_HR_START_PHR';

select 24*(to_date(V_WORKING_HR_END_KBANK,'HH24:MI') - to_date(V_WORKING_HR_START_KBANK,'HH24:MI') ),
24*(to_date(V_WORKING_HR_END_PHR,'HH24:MI') - to_date(V_WORKING_HR_START_PHR,'HH24:MI') ) into v_work_day_kbank,v_work_day_phr
from dual;

insert into DHB_STAFF_PERFORMANCE(user_id,user_name,app_input,app_assign,app_output,app_target,performance,avg_working_time,on_off_flag,team_name,create_date,status)
select u.user_name,U.FIRSTNAME || ' ' || U.LASTNAME
--, count (distinct case when  trunc(READ_DATETIME) = trunc(v_application_date)   then LT.BPD_INSTANCE_ID else null end) INPUT
, 1 INPUT --FIX 824734 INPUT = OUTPUT + ON HANDS
, count (distinct case when lt.status = '12'  and OG.APPLICATION_GROUP_ID is not null  then LT.BPD_INSTANCE_ID  else null end) ASSIGN
, count (distinct case when lt.status = '32' and  trunc(CLOSE_DATETIME) = trunc(v_application_date) and OG.APPLICATION_GROUP_ID is not null then LT.BPD_INSTANCE_ID else null end) OUTPUT
, max(nvl(decode(kbank.role_name,null,v_work_day_phr,v_work_day_kbank),0)*nvl(RP.VALUE1,0)) TARGET
,count (distinct case when lt.status = '32' and  trunc(CLOSE_DATETIME) = trunc(v_application_date) then LT.BPD_INSTANCE_ID else null end)
- max(nvl(decode(kbank.role_name,null,v_work_day_phr,v_work_day_kbank),0)*nvl(RP.VALUE1,0))  PERMFORMANCE
,f_conv_num_to_char(case when count (distinct case when lt.status = '32' and  trunc(CLOSE_DATETIME) = trunc(v_application_date) then LT.BPD_INSTANCE_ID else null end) = 0 then 0 else
sum(case when  trunc(CLOSE_DATETIME)  = trunc(v_application_date) then
(f_return_process_day(LT.READ_DATETIME, LT.CLOSE_DATETIME,decode(kbank.role_name,null,V_WORKING_HR_START_PHR,V_WORKING_HR_START_KBANK)
,decode(kbank.role_name,null,V_WORKING_HR_END_PHR,V_WORKING_HR_END_KBANK))/(60*24))else 0 end)
/count (distinct case when lt.status = '32' and  trunc(CLOSE_DATETIME) = trunc(v_application_date) then LT.BPD_INSTANCE_ID else null end)end ) ,decode(UB.INBOX_FLAG,'Y','O','F')
, team.team_id team_name,v_application_date, 'W'
from  US_USER_DETAIL u
join ms_user_team mt on mt.user_id = u.user_name
join ms_team team on team.team_id = mt.team_id
left join  LSW_USR_XREF lu on LU.USER_NAME = U.USER_NAME
left join  LSW_TASK lt on LT.USER_ID = LU.USER_ID
left join orig_application_group og  on LT.BPD_INSTANCE_ID = OG.INSTANT_ID
left join ORIG_IAS.USER_ROLE ur on UR.USER_NAME = U.USER_NAME
left join ORIG_IAS.ROLE r on r.role_id = ur.role_id
left join REPORT_PARAM rp on rp.param_type = 'BASELINE_APP' and RP.PARAM_CODE = r.role_name
left join user_inbox_info ub on ub.user_name = u.user_name
left join (SELECT SUBSTR (CSV, INSTR (CSV, ',', 1, LEV) + 1, INSTR (CSV, ',', 1, LEV + 1) - INSTR (CSV, ',', 1, LEV) - 1) ROLE_NAME
FROM (SELECT ',' || value1 || ',' CSV
        FROM REPORT_PARAM where param_type = 'STAFF_KBANK_ROLE' ),
    ( SELECT LEVEL LEV
        FROM DUAL CONNECT BY LEVEL <= 100)
WHERE LEV <= LENGTH (CSV) - LENGTH (REPLACE (CSV, ',')) - 1) kbank on kbank.role_name = r.role_name
where mt.position_id in (5)
group by u.user_name,u.firstname,u.lastname, team.team_id,decode(UB.INBOX_FLAG,'Y','O','F');

UPDATE DHB_STAFF_PERFORMANCE SET app_input = app_output + app_assign;--FIX 824734 INPUT = OUTPUT + ON HANDS
commit;

end;

PROCEDURE P_GEN_DHB_JOB_STATE
AS
BEGIN
    DELETE FROM DHB_JOB_STATE;
    INSERT INTO DHB_JOB_STATE
    SELECT
                                    SUBSTR (CSV, INSTR (CSV, ',', 1, LEV) + 1, INSTR (CSV, ',', 1, LEV + 1)
                                    - INSTR (CSV, ',', 1, LEV) - 1) JOB_STATE,
                                    PARAM_CODE
                                FROM
                                    (
                                        SELECT
                                            ',' || PARAM_VALUE || ',' CSV,
                                            PARAM_CODE
                                        FROM
                                            GENERAL_PARAM
                                        ),
                                    (
                                        SELECT
                                            LEVEL LEV
                                        FROM
                                            DUAL CONNECT BY LEVEL <= 100)
                                WHERE
                                    LEV <= LENGTH (CSV) - LENGTH (REPLACE (CSV, ',')) - 1;

  COMMIT;

END;

PROCEDURE P_GEN_DHB_LSW_TASK AS
BEGIN
   DELETE FROM DHB_LSW_TASK;
   COMMIT;
   INSERT INTO DHB_LSW_TASK
   SELECT DISTINCT BPD_INSTANCE_ID, USER_ID
   FROM
   LSW_TASK LT
  WHERE
  STATUS = '32'
   AND TO_CHAR(LT.CLOSE_DATETIME,'YYYYMM') = TO_CHAR(SYSDATE,'YYYYMM');
   COMMIT;
END;

PROCEDURE P_GEN_DHB_COMPARISON_DATA
AS
BEGIN

    --IMPROVE QUERY PERFORMANCE
    COMMIT;
    SET TRANSACTION READ ONLY;

    FOR CUR IN (SELECT APPLICATION_GROUP_ID, CREATE_BY, COUNT(*) QUANTITY FROM ORIG_COMPARISON_DATA OCD
        WHERE OCD.COMPARE_FLAG = 'W' AND OCD.SRC_OF_DATA = '2MAKER'
        AND TRUNC(OCD.UPDATE_DATE, 'MONTH') = TRUNC (SYSDATE, 'MONTH')
        GROUP BY APPLICATION_GROUP_ID, CREATE_BY)
    LOOP
        --SET TRANSACTION BACK TO DEFAULT
        COMMIT;
        INSERT INTO DHB_COMPARISON_DATA VALUES (CUR.APPLICATION_GROUP_ID, CUR.CREATE_BY, CUR.QUANTITY);

    END LOOP;
    COMMIT;

END;

PROCEDURE P_GEN_PRECAL_LEG

AS
    V_INFINITE_TYPES VARCHAR2(1000);
    V_WISDOM_TYPES VARCHAR2(1000);
    V_PREMIER_TYPES VARCHAR2(1000);
BEGIN
    DELETE FROM DHB_PRECAL_LEG;

    --PREPARE DATA
    BEGIN
        SELECT PARAM_VALUE INTO V_INFINITE_TYPES FROM GENERAL_PARAM WHERE PARAM_CODE = 'CC_INFINITE';
    EXCEPTION WHEN OTHERS THEN
        V_INFINITE_TYPES := '';
    END;
     BEGIN
        SELECT PARAM_VALUE INTO V_WISDOM_TYPES FROM GENERAL_PARAM WHERE PARAM_CODE = 'CC_WISDOM';
    EXCEPTION WHEN OTHERS THEN
        V_INFINITE_TYPES := '';
    END;
       BEGIN
        SELECT PARAM_VALUE INTO V_PREMIER_TYPES FROM GENERAL_PARAM WHERE PARAM_CODE = 'CC_PREMIER';
    EXCEPTION WHEN OTHERS THEN
        V_INFINITE_TYPES := '';
    END;
    FOR SLA IN (SELECT * FROM MS_SLA)
    LOOP
        --CASE KEC/KPL
        IF(SLA.PRODUCT_TYPE = 'KEC') THEN
            -- INSERT KEC CONFIG
            FOR CARD IN (SELECT * FROM CARD_TYPE WHERE BUSINESS_CLASS_ID LIKE SLA.PRODUCT_TYPE||'_%')
            LOOP
                INSERT INTO "ORIG_APP"."DHB_PRECAL_LEG" ("PRODUCT_TYPE", "CARD_TYPE", "INCREASE_FLAG", "SLA_ID", "SLA_CODE", "SLA_DESC", "LEG1", "LEG2", "LEG3", "LEG4", "CREATE_DATE", "CREATE_BY", "UPDATE_DATE", "UPDATE_BY")
                VALUES (SLA.PRODUCT_TYPE, CARD.CARD_TYPE_ID, SLA.INCREASE_FLAG, SLA.SLA_ID, SLA.SLA_CODE, SLA.SLA_DESC, SLA.LEG1, SLA.LEG2, SLA.LEG3, SLA.LEG4, SYSDATE, 'SYSTEM', SYSDATE, 'SYSTEM');
            END LOOP;

        ELSIF (SLA.PRODUCT_TYPE = 'KPL') THEN

            --INSERT KPL CONFIG
            INSERT INTO "ORIG_APP"."DHB_PRECAL_LEG" ("PRODUCT_TYPE", "CARD_TYPE", "INCREASE_FLAG", "SLA_ID", "SLA_CODE", "SLA_DESC", "LEG1", "LEG2", "LEG3", "LEG4", "CREATE_DATE", "CREATE_BY", "UPDATE_DATE", "UPDATE_BY")
            VALUES (SLA.PRODUCT_TYPE, NULL, SLA.INCREASE_FLAG, SLA.SLA_ID, SLA.SLA_CODE, SLA.SLA_DESC, SLA.LEG1, SLA.LEG2, SLA.LEG3, SLA.LEG4, SYSDATE, 'SYSTEM', SYSDATE, 'SYSTEM');
        ELSE
            IF(SLA.CARD_TYPE = 'CC_INFINITE') THEN
                FOR CARD IN (SELECT * FROM CARD_TYPE WHERE BUSINESS_CLASS_ID LIKE 'CC_%' AND INSTR(V_INFINITE_TYPES, CARD_TYPE_ID) > 0)
                LOOP
                    INSERT INTO "ORIG_APP"."DHB_PRECAL_LEG" ("PRODUCT_TYPE", "CARD_TYPE", "INCREASE_FLAG", "SLA_ID", "SLA_CODE", "SLA_DESC", "LEG1", "LEG2", "LEG3", "LEG4", "CREATE_DATE", "CREATE_BY", "UPDATE_DATE", "UPDATE_BY")
                    VALUES (SLA.PRODUCT_TYPE, CARD.CARD_TYPE_ID, SLA.INCREASE_FLAG, SLA.SLA_ID, SLA.SLA_CODE, SLA.SLA_DESC, SLA.LEG1, SLA.LEG2, SLA.LEG3, SLA.LEG4, SYSDATE, 'SYSTEM', SYSDATE, 'SYSTEM');
                END LOOP;

            ELSIF(SLA.CARD_TYPE = 'CC_WISDOM') THEN
                FOR CARD IN (SELECT * FROM CARD_TYPE WHERE BUSINESS_CLASS_ID LIKE 'CC_%' AND INSTR(V_WISDOM_TYPES, CARD_TYPE_ID) > 0)
                LOOP
                    INSERT INTO "ORIG_APP"."DHB_PRECAL_LEG" ("PRODUCT_TYPE", "CARD_TYPE", "INCREASE_FLAG", "SLA_ID", "SLA_CODE", "SLA_DESC", "LEG1", "LEG2", "LEG3", "LEG4", "CREATE_DATE", "CREATE_BY", "UPDATE_DATE", "UPDATE_BY")
                    VALUES (SLA.PRODUCT_TYPE, CARD.CARD_TYPE_ID, SLA.INCREASE_FLAG, SLA.SLA_ID, SLA.SLA_CODE, SLA.SLA_DESC, SLA.LEG1, SLA.LEG2, SLA.LEG3, SLA.LEG4, SYSDATE, 'SYSTEM', SYSDATE, 'SYSTEM');
                END LOOP;

            ELSIF(SLA.CARD_TYPE = 'CC_PREMIER') THEN
                FOR CARD IN (SELECT * FROM CARD_TYPE WHERE BUSINESS_CLASS_ID LIKE 'CC_%' AND INSTR(V_PREMIER_TYPES, CARD_TYPE_ID) > 0)
                LOOP
                    INSERT INTO "ORIG_APP"."DHB_PRECAL_LEG" ("PRODUCT_TYPE", "CARD_TYPE", "INCREASE_FLAG", "SLA_ID", "SLA_CODE", "SLA_DESC", "LEG1", "LEG2", "LEG3", "LEG4", "CREATE_DATE", "CREATE_BY", "UPDATE_DATE", "UPDATE_BY")
                    VALUES (SLA.PRODUCT_TYPE, CARD.CARD_TYPE_ID, SLA.INCREASE_FLAG, SLA.SLA_ID, SLA.SLA_CODE, SLA.SLA_DESC, SLA.LEG1, SLA.LEG2, SLA.LEG3, SLA.LEG4, SYSDATE, 'SYSTEM', SYSDATE, 'SYSTEM');
                END LOOP;
            ELSIF(SLA.CARD_TYPE = '1') THEN --CASE PLATINUM
                FOR CARD IN (SELECT * FROM CARD_TYPE WHERE BUSINESS_CLASS_ID LIKE 'CC_%' AND CARD_LEVEL = 1 AND INSTR(V_INFINITE_TYPES||','||V_WISDOM_TYPES||','||V_PREMIER_TYPES, CARD_TYPE_ID) < 1)
                LOOP
                    INSERT INTO "ORIG_APP"."DHB_PRECAL_LEG" ("PRODUCT_TYPE", "CARD_TYPE", "INCREASE_FLAG", "SLA_ID", "SLA_CODE", "SLA_DESC", "LEG1", "LEG2", "LEG3", "LEG4", "CREATE_DATE", "CREATE_BY", "UPDATE_DATE", "UPDATE_BY")
                    VALUES (SLA.PRODUCT_TYPE, CARD.CARD_TYPE_ID, SLA.INCREASE_FLAG, SLA.SLA_ID, SLA.SLA_CODE, SLA.SLA_DESC, SLA.LEG1, SLA.LEG2, SLA.LEG3, SLA.LEG4, SYSDATE, 'SYSTEM', SYSDATE, 'SYSTEM');
                END LOOP;
            ELSE --OTHER CC
                 FOR CARD IN (SELECT * FROM CARD_TYPE WHERE BUSINESS_CLASS_ID LIKE 'CC_%' AND CARD_LEVEL <> 1)
                LOOP
                    INSERT INTO "ORIG_APP"."DHB_PRECAL_LEG" ("PRODUCT_TYPE", "CARD_TYPE", "INCREASE_FLAG", "SLA_ID", "SLA_CODE", "SLA_DESC", "LEG1", "LEG2", "LEG3", "LEG4", "CREATE_DATE", "CREATE_BY", "UPDATE_DATE", "UPDATE_BY")
                    VALUES (SLA.PRODUCT_TYPE, CARD.CARD_TYPE_ID, SLA.INCREASE_FLAG, SLA.SLA_ID, SLA.SLA_CODE, SLA.SLA_DESC, SLA.LEG1, SLA.LEG2, SLA.LEG3, SLA.LEG4, SYSDATE, 'SYSTEM', SYSDATE, 'SYSTEM');
                END LOOP;
            END IF;
        END IF;
        COMMIT;
    END LOOP;
END;

/*
PROCEDURE P_GEN_DHB_LEG
AS
BEGIN
  DELETE FROM DHB_LEG ;
  INSERT INTO DHB_LEG
  SELECT S.*,
          LCL.CARD_LEVEL,
          LCT.LIST_CARD_TYPE,
          LCX.LIST_CARD_TYPE NOT_LIST_CARD_TYPE
        FROM MS_SLA S
        LEFT JOIN
          (SELECT SUBSTR (CSV, INSTR (CSV, ',', 1, LEV) + 1, INSTR (CSV, ',', 1, LEV + 1) - INSTR (CSV, ',', 1, LEV) - 1) CARD_LEVEL,
            SLA_ID
          FROM
            (SELECT ','
              || CARD_TYPE
              || ',' CSV,
              SLA_ID
            FROM MS_SLA
            WHERE CARD_PARAM = 'CARD_LEVEL'
            ),
            ( SELECT LEVEL LEV FROM DUAL CONNECT BY LEVEL <= 100
            )
          WHERE LEV <= LENGTH (CSV) - LENGTH (REPLACE (CSV, ',')) - 1
          ) LCL
        ON LCL.SLA_ID = S.SLA_ID
        LEFT JOIN
          (SELECT SUBSTR (CSV, INSTR (CSV, ',', 1, LEV) + 1, INSTR (CSV, ',', 1, LEV + 1) - INSTR (CSV, ',', 1, LEV) - 1) LIST_CARD_TYPE,
            PARAM_CODE
          FROM
            (SELECT ','
              || PARAM_VALUE
              || ',' CSV,
              PARAM_CODE
            FROM GENERAL_PARAM
            WHERE PARAM_CODE IN ('CC_INFINITE','CC_PREMIER','CC_WISDOM')
            ),
            ( SELECT LEVEL LEV FROM DUAL CONNECT BY LEVEL <= 100
            )
          WHERE LEV <= LENGTH (CSV) - LENGTH (REPLACE (CSV, ',')) - 1
          ) LCT
        ON LCT.PARAM_CODE = S.CARD_TYPE
        AND S.CARD_PARAM  = 'CARD_TYPE'
        LEFT JOIN
          (SELECT SUBSTR (CSV, INSTR (CSV, ',', 1, LEV) + 1, INSTR (CSV, ',', 1, LEV + 1) - INSTR (CSV, ',', 1, LEV) - 1) LIST_CARD_TYPE,
            PARAM_CODE
          FROM
            (SELECT ','
              || PARAM_VALUE
              || ',' CSV,
              PARAM_CODE
            FROM GENERAL_PARAM
            WHERE PARAM_CODE IN ('CC_INFINITE','CC_PREMIER','CC_WISDOM')
            ),
            ( SELECT LEVEL LEV FROM DUAL CONNECT BY LEVEL <= 100
            )
          WHERE LEV <= LENGTH (CSV) - LENGTH (REPLACE (CSV, ',')) - 1
          ) LCX
        ON LCL.CARD_LEVEL  = 1;
        COMMIT;

END;
*/

PROCEDURE P_GEN_DHB_TEAM_HOUR
AS
V_APPLICATION_DATE DATE;
V_DHB_TYPE VARCHAR2(2);
V_DHB_GROUP NUMBER;
V_DHB_SEQ NUMBER;
V_ACCUM_OUTPUT_OF_TEAM NUMBER;
V_CAPACITY_OF_TEAM NUMBER;
V_SCHEDULE_ID NUMBER;
V_CNT NUMBER := 0;
begin

    SELECT TRUNC(APP_DATE) + (SYSDATE - TRUNC(SYSDATE))
    INTO  V_APPLICATION_DATE
    FROM  APPLICATION_DATE;

    SELECT MAX(SCHEDULE_ID) INTO V_SCHEDULE_ID
    FROM DHB_SCHEDULE;

    DELETE FROM DHB_TEAM_HOUR;

--OUTPUT
V_DHB_TYPE := '03';
V_DHB_GROUP := 1;
V_DHB_SEQ :=1;


              INSERT INTO DHB_TEAM_HOUR
              (TEAM_ID,SCHEDULE_ID,DHB_TYPE,DHB_GROUP ,DHP_SEQ
              ,OUTPUT
              --,APP_PER_DAY_ACTUAL
              ,CREATE_DATE)
              SELECT MU.TEAM_ID,V_SCHEDULE_ID,V_DHB_TYPE,V_DHB_GROUP,V_DHB_SEQ
                    ,COUNT(DISTINCT LT.TASK_ID)
                    ,V_APPLICATION_DATE
              FROM LSW_TASK LT
              JOIN LSW_USR_XREF LU ON LU.USER_ID = LT.USER_ID
              JOIN MS_USER_TEAM MU ON LU.USER_NAME = MU.USER_ID
              WHERE LT.STATUS = '32'
              AND LT.CLOSE_DATETIME BETWEEN TRUNC(V_APPLICATION_DATE,'HH24') AND TRUNC(V_APPLICATION_DATE + 1/24,  'HH24')
              GROUP BY MU.TEAM_ID,V_SCHEDULE_ID,V_DHB_TYPE,V_DHB_GROUP,V_DHB_SEQ,V_APPLICATION_DATE;



              /*
              --Query from Table DHB_STAFF_PERFORMANCE
              INSERT INTO DHB_TEAM
              (TEAM_ID,SCHEDULE_ID,DHB_TYPE,DHB_GROUP ,DHP_SEQ
              ,OUTPUT
              ,CREATE_DATE)
              SELECT MU.TEAM_ID,V_SCHEDULE_ID,V_DHB_TYPE,V_DHB_GROUP,V_DHB_SEQ
                    ,SUM(APP_OUTPUT)
                    ,V_APPLICATION_DATE
              FROM DHB_STAFF_PERFORMANCE ST
              JOIN MS_USER_TEAM MU ON ST.USER_ID = MU.USER_ID
              GROUP BY MU.TEAM_ID,V_SCHEDULE_ID,V_DHB_TYPE,V_DHB_GROUP,V_DHB_SEQ,V_APPLICATION_DATE;
              */

              INSERT INTO DHB_TEAM_HOUR
              (TEAM_ID,SCHEDULE_ID,DHB_TYPE,DHB_GROUP ,DHP_SEQ
              ,CAPACITY_OF_TEAM
              ,CREATE_DATE)
              SELECT TEAM_ID,V_SCHEDULE_ID,V_DHB_TYPE,V_DHB_GROUP,V_DHB_SEQ
                     ,SUM(BASE_LINE_ALL)
                     ,V_APPLICATION_DATE
                      FROM
                          (
                              SELECT DISTINCT
                                  U.USER_ID,
                                  U.TEAM_ID,
                                  (RP.VALUE1 * ( TO_DATE(
                                      CASE
                                          WHEN TO_CHAR(TRUNC(SYSDATE+ 1/24,'HH24'),'HH24:MI') > END_TIME.VALUE1
                                          THEN END_TIME.VALUE1
                                          ELSE TO_CHAR(TRUNC(SYSDATE+ 1/24,'HH24'),'HH24:MI')
                                      END ,'HH24:MI') - TO_DATE(
                                      CASE
                                          WHEN TO_CHAR(TRUNC(SYSDATE,'HH24'),'HH24:MI') < LPAD(START_TIME.VALUE1,5,'0')
                                          THEN LPAD(START_TIME.VALUE1,5,'0')
                                          ELSE TO_CHAR(TRUNC(SYSDATE,'HH24'),'HH24:MI')
                                      END,'HH24:MI')) *24/1)    BASE_LINE_ALL ,
                                  END_TIME.VALUE1               END_TIME,
                                  LPAD(START_TIME.VALUE1,5,'0') START_TIME ,
                                  ( TO_DATE(
                                      CASE
                                          WHEN TO_CHAR(TRUNC(SYSDATE+ 1/24,'HH24'),'HH24:MI') > END_TIME.VALUE1
                                          THEN END_TIME.VALUE1
                                          ELSE TO_CHAR(TRUNC(SYSDATE+ 1/24,'HH24'),'HH24:MI')
                                      END ,'HH24:MI') - TO_DATE(
                                      CASE
                                          WHEN TO_CHAR(TRUNC(SYSDATE,'HH24'),'HH24:MI') < LPAD(START_TIME.VALUE1,5,'0')
                                          THEN LPAD(START_TIME.VALUE1,5,'0')
                                          ELSE TO_CHAR(TRUNC(SYSDATE,'HH24'),'HH24:MI')
                                      END,'HH24:MI')) *24/1DURATION
                              FROM MS_USER_TEAM U
                              JOIN ORIG_IAS.USER_ROLE UR ON  UR.USER_NAME = U.USER_ID
                              JOIN ORIG_IAS.ROLE R  ON R.ROLE_ID = UR.ROLE_ID
                              JOIN REPORT_PARAM RP ON RP.PARAM_TYPE = 'BASELINE_APP'
                              AND RP.PARAM_CODE = R.ROLE_NAME
                              LEFT JOIN
                                  (
                                      SELECT
                                          SUBSTR (CSV, INSTR (CSV, ',', 1, LEV) + 1, INSTR (CSV, ',', 1, LEV + 1) - INSTR
                                          (CSV, ',', 1, LEV) - 1) ROLE_NAME
                                      FROM
                                          (  SELECT ',' || VALUE1 || ',' CSV
                                              FROM  REPORT_PARAM
                                              WHERE PARAM_TYPE = 'STAFF_KBANK_ROLE' ),
                                          (
                                              SELECT  LEVEL LEV
                                              FROM DUAL CONNECT BY LEVEL <= 100)
                                      WHERE
                                          LEV <= LENGTH (CSV) - LENGTH (REPLACE (CSV, ',')) - 1) KBANK
                              ON
                                  KBANK.ROLE_NAME = R.ROLE_NAME
                              JOIN
                                  WORKFLOW_PARAM END_TIME
                              ON
                                  END_TIME.PARAM_CODE = 'WORKING_HR_END_' || DECODE(KBANK.ROLE_NAME,NULL,'PHR','KBANK')
                              JOIN
                                  WORKFLOW_PARAM START_TIME
                              ON
                                  START_TIME.PARAM_CODE = 'WORKING_HR_START_' || DECODE(KBANK.ROLE_NAME,NULL,'PHR', 'KBANK')

                            )
                   GROUP BY TEAM_ID,V_SCHEDULE_ID,V_DHB_TYPE,V_DHB_GROUP,V_DHB_SEQ,V_APPLICATION_DATE;

end P_GEN_DHB_TEAM_HOUR;

PROCEDURE P_GEN_DHB_TEAM
AS
V_APPLICATION_DATE DATE;
V_DHB_TYPE VARCHAR2(2) := '02';
--V_POSITION_LEVEL VARCHAR2(30) := '';
V_DHB_GROUP NUMBER;
V_DHB_SEQ NUMBER;
--V_INPUT NUMBER;
--V_OUTPUT NUMBER;
--V_INCOMING_APP_ALL NUMBER;
--V_INCOMING_APP_URGENT NUMBER;
--V_ON_HAND_WIP NUMBER;
--V_ON_HAND_URGENT NUMBER;
V_SCHEDULE_ID NUMBER;
V_CNT NUMBER := 0;
begin

    SELECT TRUNC(APP_DATE) + (SYSDATE - TRUNC(SYSDATE))
    INTO  V_APPLICATION_DATE
    FROM  APPLICATION_DATE;

    SELECT MAX(SCHEDULE_ID) INTO V_SCHEDULE_ID
    FROM DHB_SCHEDULE;

     --SELECT DHB_SCHEDULE_ID_SEQ.NEXTVAL INTO V_SCHEDULE_ID
     --FROM DUAL ;

     --SELECT COUNT(1) INTO V_CNT
     --FROM DHB_SCHEDULE;

--     IF V_CNT = 0 THEN
--        INSERT INTO DHB_SCHEDULE
--         (SCHEDULE_ID
--         ,CREATE_DATE
--         ,UPDATE_DATE)
--        VALUES
--         (V_SCHEDULE_ID
--          ,V_APPLICATION_DATE
--          ,V_APPLICATION_DATE
--          );
--     ELSE
--
--         UPDATE DHB_SCHEDULE
--         SET SCHEDULE_ID = V_SCHEDULE_ID
--            ,CREATE_DATE = V_APPLICATION_DATE
--            ,UPDATE_DATE = V_APPLICATION_DATE;
--
--     END IF;

     DELETE FROM DHB_TEAM WHERE V_DHB_TYPE = '02';

--INCOMING_APP_ALL
--INCOMING_APP_URGENT
V_DHB_TYPE := '02';
V_DHB_GROUP := 1;
V_DHB_SEQ :=3;

--          INSERT INTO DHB_TEAM
--          (  TEAM_ID
--            ,SCHEDULE_ID
--            ,DHB_TYPE
--            ,DHB_GROUP
--            ,DHP_SEQ
--            ,INCOMING_APP_ALL
--            ,INCOMING_APP_URGENT
--            ,CREATE_DATE)
--           SELECT MU.TEAM_ID,V_SCHEDULE_ID,V_DHB_TYPE,V_DHB_GROUP,V_DHB_SEQ
--                  ,COUNT(DISTINCT OG.APPLICATION_GROUP_ID)
--                  ,COUNT(DISTINCT CASE WHEN OG.PRIORITY IN (1,2)THEN OG.APPLICATION_GROUP_ID ELSE NULL END)
--                  ,V_APPLICATION_DATE
--            FROM ORIG_APPLICATION_GROUP OG
--            JOIN LSW_TASK LT ON LT.BPD_INSTANCE_ID = OG.INSTANT_ID
--            JOIN LSW_USR_XREF LU ON LU.USER_ID = LT.USER_ID
--            JOIN MS_USER_TEAM MU ON LU.USER_NAME = MU.USER_ID
--            JOIN ORIG_IAS.USER_ROLE UR ON MU.USER_ID = UR.USER_NAME
--            JOIN ORIG_IAS.ROLE R ON R.ROLE_ID = UR.ROLE_ID
--            JOIN (SELECT JOB_STATE.JOB_STATE,
--                        INCOMING.INCOMING_CODE,
--                        INCOMING.PARAM_CODE
--                    FROM
--                        (SELECT SUBSTR (CSV, INSTR (CSV, ',', 1, LEV) + 1, INSTR (CSV, ',', 1, LEV + 1) - INSTR(CSV, ',', 1, LEV) - 1) JOB_STATE,
--                                PARAM_CODE
--                         FROM
--                                (SELECT',' || PARAM_VALUE || ',' CSV,PARAM_CODE FROM GENERAL_PARAM ),
--                                (SELECT LEVEL LEV FROM DUAL CONNECT BY LEVEL <= 100)
--                         WHERE LEV <= LENGTH (CSV) - LENGTH (REPLACE (CSV, ',')) - 1) JOB_STATE
--                    JOIN
--                        (
--                            SELECT
--                                SUBSTR (CSV, INSTR (CSV, ',', 1, LEV) + 1, INSTR (CSV, ',', 1, LEV + 1) - INSTR
--                                (CSV, ',', 1, LEV) - 1) INCOMING_CODE,
--                                PARAM_CODE
--                            FROM
--                                (
--                                    SELECT
--                                        ',' || PARAM_VALUE2 || ',' CSV,
--                                        PARAM_CODE
--                                    FROM
--                                        GENERAL_PARAM ),
--                                (
--                                    SELECT
--                                        LEVEL LEV
--                                    FROM
--                                        DUAL CONNECT BY LEVEL <= 100)
--                            WHERE
--                                LEV <= LENGTH (CSV) - LENGTH (REPLACE (CSV, ',')) - 1) INCOMING
--                    ON
--                        INCOMING.INCOMING_CODE = JOB_STATE.PARAM_CODE
--                    WHERE
--                        INCOMING.PARAM_CODE LIKE 'WIP_JOBSTATE%') T
--            ON
--                ( (
--                        T.JOB_STATE = OG.JOB_STATE
--                    AND T.PARAM_CODE = 'WIP_JOBSTATE_' || R.ROLE_NAME )
--                OR  (
--                        R.ROLE_NAME IN ('DE1_1',
--                                        'IA',
--                                        'DE1_2')
--                    AND EXISTS
--                        (
--                            SELECT
--                                1
--                            FROM
--                                (
--                                    SELECT
--                                        SUBSTR (CSV, INSTR (CSV, ',', 1, LEV) + 1, INSTR (CSV, ',', 1, LEV + 1)
--                                        - INSTR (CSV, ',', 1, LEV) - 1) JOB_STATE,
--                                        PARAM_CODE
--                                    FROM
--                                        (
--                                            SELECT
--                                                ',' || PARAM_VALUE || ',' CSV,
--                                                PARAM_CODE
--                                            FROM
--                                                GENERAL_PARAM
--                                            WHERE
--                                                PARAM_CODE LIKE 'WIP_JOBSTATE_FU' ),
--                                        (
--                                            SELECT
--                                                LEVEL LEV
--                                            FROM
--                                                DUAL CONNECT BY LEVEL <= 100)
--                                    WHERE
--                                        LEV <= LENGTH (CSV) - LENGTH (REPLACE (CSV, ',')) - 1) T
--                            WHERE
--                                T.JOB_STATE = OG.JOB_STATE
--                            AND OG.PREV_JOB_STATE IN
--                                (
--                                    SELECT
--                                        SUBSTR (CSV, INSTR (CSV, ',', 1, LEV) + 1, INSTR (CSV, ',', 1, LEV + 1)
--                                        - INSTR (CSV, ',', 1, LEV) - 1) JOB_STATE
--                                    FROM
--                                        (
--                                            SELECT
--                                                ',' || PARAM_VALUE || ',' CSV,
--                                                PARAM_CODE
--                                            FROM
--                                                GENERAL_PARAM
--                                            WHERE
--                                                PARAM_CODE IN ( 'WIP_JOBSTATE_IA' ,
--                                                               'WIP_JOBSTATE_DE1_1',
--                                                               'WIP_JOBSTATE_DE1_2')),
--                                        (
--                                            SELECT
--                                                LEVEL LEV
--                                            FROM
--                                                DUAL CONNECT BY LEVEL <= 100)
--                                    WHERE
--                                        LEV <= LENGTH (CSV) - LENGTH (REPLACE (CSV, ',')) - 1))))
--            WHERE
--                ((
--                        R.ROLE_NAME != 'VT'
--                    AND R.ROLE_NAME !='CA')
--                OR  (
--                        R.ROLE_NAME != 'VT'
--                    AND EXISTS
--                        (
--                            SELECT
--                                1
--                            FROM
--                                ORIG_APPLICATION AP
--                            WHERE
--                                AP.APPLICATION_GROUP_ID = OG.APPLICATION_GROUP_ID
--                            AND (
--                                    LIFE_CYCLE > 1
--                                OR  OG.LAST_DECISION = 'RE') )) )
--          GROUP BY MU.TEAM_ID,V_SCHEDULE_ID,V_DHB_TYPE,V_DHB_GROUP,V_DHB_SEQ,V_APPLICATION_DATE;

--IN_QUEUE_ALL
--IN_QUEUE_URGENT
V_DHB_TYPE := '02';
V_DHB_GROUP := 1;
V_DHB_SEQ :=4;

            INSERT INTO DHB_TEAM
              (TEAM_ID,SCHEDULE_ID,DHB_TYPE,DHB_GROUP ,DHP_SEQ
               ,IN_QUEUE_ALL
               ,IN_QUEUE_URGENT
               ,CREATE_DATE)
              SELECT MU.TEAM_ID,V_SCHEDULE_ID,V_DHB_TYPE,V_DHB_GROUP,V_DHB_SEQ
                    ,COUNT(DISTINCT OG.APPLICATION_GROUP_ID)
                    ,COUNT(DISTINCT CASE WHEN OG.PRIORITY IN (1,2)
                                        THEN OG.APPLICATION_GROUP_ID
                                    ELSE NULL END)
                    ,V_APPLICATION_DATE
            FROM ORIG_APPLICATION_GROUP OG
            JOIN LSW_TASK LT ON LT.BPD_INSTANCE_ID = OG.INSTANT_ID AND LT.STATUS = 12
            JOIN LSW_USR_XREF LU ON LU.USER_ID = LT.USER_ID
            JOIN MS_USER_TEAM MU ON LU.USER_NAME = MU.USER_ID
            JOIN ORIG_IAS.USER_ROLE UR ON UR.USER_NAME = MU.USER_ID
            JOIN ORIG_IAS.ROLE R ON R.ROLE_ID = UR.ROLE_ID
            JOIN ( SELECT SUBSTR (CSV, INSTR (CSV, ',', 1, LEV) + 1, INSTR (CSV, ',', 1, LEV + 1) - INSTR (CSV, ',', 1, LEV) - 1) JOB_STATE,
                    PARAM_CODE
                    FROM
                        (  SELECT ',' || PARAM_VALUE || ',' CSV,
                                PARAM_CODE
                            FROM GENERAL_PARAM
                            WHERE PARAM_CODE LIKE 'WIP_JOBSTATE_%' ),
                        ( SELECT  LEVEL LEV FROM  DUAL CONNECT BY LEVEL <= 100)
                    WHERE
                    LEV <= LENGTH (CSV) - LENGTH (REPLACE (CSV, ',')) - 1) T
            ON T.JOB_STATE = OG.JOB_STATE
            AND T.PARAM_CODE = 'WIP_JOBSTATE_' || R.ROLE_NAME
            --JOIN MS_USER_TEAM U ON U.USER_ID = UR.USER_NAME
--            AND LT.TASK_ID =( SELECT MAX(TASK_ID)
--                              FROM LSW_TASK X
--                              WHERE X.BPD_INSTANCE_ID = LT.BPD_INSTANCE_ID)
--            AND LT.STATUS = 12
--            AND LT.USER_ID = -1
            --WHERE EXISTS(SELECT '' FROM LSW_TASK LT
            --              WHERE LT.BPD_INSTANCE_ID = OG.INSTANT_ID
            --              AND LT.TASK_ID =( SELECT MAX(TASK_ID)
            --                                FROM LSW_TASK X
            --                                WHERE X.BPD_INSTANCE_ID = LT.BPD_INSTANCE_ID)
            --               AND LT.STATUS = 12
            --               AND LT.USER_ID = -1)
            GROUP BY MU.TEAM_ID,V_SCHEDULE_ID,V_DHB_TYPE,V_DHB_GROUP,V_DHB_SEQ,V_APPLICATION_DATE;

--INPUT
V_DHB_TYPE := '02';
V_DHB_GROUP := 1;
V_DHB_SEQ :=5;

              --Query from Table DHB_STAFF_PERFORMANCE
              /*
              INSERT INTO DHB_TEAM
              (TEAM_ID,SCHEDULE_ID,DHB_TYPE,DHB_GROUP ,DHP_SEQ
              ,INPUT
              ,CREATE_DATE)
              SELECT MU.TEAM_ID,V_SCHEDULE_ID,V_DHB_TYPE,V_DHB_GROUP,V_DHB_SEQ
                    ,SUM(APP_INPUT)
                    ,V_APPLICATION_DATE
              FROM DHB_STAFF_PERFORMANCE ST
              JOIN MS_USER_TEAM MU ON ST.USER_ID = MU.USER_ID
              GROUP BY MU.TEAM_ID,V_SCHEDULE_ID,V_DHB_TYPE,V_DHB_GROUP,V_DHB_SEQ,V_APPLICATION_DATE;
              */

              INSERT INTO DHB_TEAM
              (TEAM_ID,SCHEDULE_ID,DHB_TYPE,DHB_GROUP ,DHP_SEQ
              ,INPUT
              ,CREATE_DATE)
              SELECT MU.TEAM_ID,V_SCHEDULE_ID,V_DHB_TYPE,V_DHB_GROUP,V_DHB_SEQ
                    ,COUNT(DISTINCT LT.BPD_INSTANCE_ID)
                    ,V_APPLICATION_DATE
              FROM ORIG_APPLICATION_GROUP OG
              JOIN  LSW_TASK LT ON LT.BPD_INSTANCE_ID = OG.INSTANT_ID
              JOIN LSW_USR_XREF LU ON LU.USER_ID = LT.USER_ID
              JOIN MS_USER_TEAM MU ON LU.USER_NAME = MU.USER_ID
              WHERE LT.READ_DATETIME BETWEEN TRUNC(V_APPLICATION_DATE)  AND (TRUNC(V_APPLICATION_DATE)+(1-0.00001))
              GROUP BY MU.TEAM_ID,V_SCHEDULE_ID,V_DHB_TYPE,V_DHB_GROUP,V_DHB_SEQ,V_APPLICATION_DATE;


--V_ON_HAND_WIP
--V_ON_HAND_URGENT
V_DHB_TYPE := '02';
V_DHB_GROUP := 1;
V_DHB_SEQ :=6;

              INSERT INTO DHB_TEAM
              (TEAM_ID,SCHEDULE_ID,DHB_TYPE,DHB_GROUP ,DHP_SEQ
              ,ON_HAND_WIP
              ,ON_HAND_URGENT
              ,CREATE_DATE)
              SELECT MU.TEAM_ID,V_SCHEDULE_ID,V_DHB_TYPE,V_DHB_GROUP,V_DHB_SEQ
                    ,COUNT(DISTINCT LT.BPD_INSTANCE_ID)
                    ,COUNT(DISTINCT CASE WHEN OG.PRIORITY IN (1,2) THEN OG.INSTANT_ID ELSE NULL END)
                    ,V_APPLICATION_DATE
              FROM ORIG_APPLICATION_GROUP OG
              JOIN LSW_TASK LT ON LT.BPD_INSTANCE_ID = OG.INSTANT_ID
              JOIN LSW_USR_XREF LU ON LU.USER_ID = LT.USER_ID
              JOIN MS_USER_TEAM MU ON LU.USER_NAME = MU.USER_ID
              WHERE LT.STATUS = '12'
              GROUP BY MU.TEAM_ID,V_SCHEDULE_ID,V_DHB_TYPE,V_DHB_GROUP,V_DHB_SEQ,V_APPLICATION_DATE;

--OUTPUT
V_DHB_TYPE := '02';
V_DHB_GROUP := 1;
V_DHB_SEQ :=7;

                /*
              --Query from Table DHB_STAFF_PERFORMANCE
              INSERT INTO DHB_TEAM
              (TEAM_ID,SCHEDULE_ID,DHB_TYPE,DHB_GROUP ,DHP_SEQ
              ,OUTPUT
              ,CREATE_DATE)
              SELECT MU.TEAM_ID,V_SCHEDULE_ID,V_DHB_TYPE,V_DHB_GROUP,V_DHB_SEQ
                    ,SUM(APP_OUTPUT)
                    ,V_APPLICATION_DATE
              FROM DHB_STAFF_PERFORMANCE ST
              JOIN MS_USER_TEAM MU ON ST.USER_ID = MU.USER_ID
              GROUP BY MU.TEAM_ID,V_SCHEDULE_ID,V_DHB_TYPE,V_DHB_GROUP,V_DHB_SEQ,V_APPLICATION_DATE;
              */

              INSERT INTO DHB_TEAM
              (TEAM_ID,SCHEDULE_ID,DHB_TYPE,DHB_GROUP ,DHP_SEQ
              ,OUTPUT
              --,APP_PER_DAY_ACTUAL
              ,CREATE_DATE)
              SELECT MU.TEAM_ID,V_SCHEDULE_ID,V_DHB_TYPE,V_DHB_GROUP,V_DHB_SEQ
                    ,COUNT(DISTINCT LT.BPD_INSTANCE_ID)
                    --,COUNT(DISTINCT LT.BPD_INSTANCE_ID)
                    ,V_APPLICATION_DATE
              FROM ORIG_APPLICATION_GROUP OG
              JOIN  LSW_TASK LT ON LT.BPD_INSTANCE_ID = OG.INSTANT_ID
              JOIN LSW_USR_XREF LU ON LU.USER_ID = LT.USER_ID
              JOIN MS_USER_TEAM MU ON LU.USER_NAME = MU.USER_ID
              WHERE LT.CLOSE_DATETIME BETWEEN TRUNC(V_APPLICATION_DATE)  AND (TRUNC(V_APPLICATION_DATE)+(1-0.00001))
              AND LT.STATUS = '32'
              GROUP BY MU.TEAM_ID,V_SCHEDULE_ID,V_DHB_TYPE,V_DHB_GROUP,V_DHB_SEQ,V_APPLICATION_DATE;

end P_GEN_DHB_TEAM;

PROCEDURE P_GEN_DHB_TEAM_DAY
AS
V_APPLICATION_DATE DATE;
V_DHB_TYPE VARCHAR2(2) := '01';
--V_POSITION_LEVEL VARCHAR2(30) := '';
V_DHB_GROUP NUMBER;
V_DHB_SEQ NUMBER;
--V_INPUT NUMBER;
--V_OUTPUT NUMBER;
--V_INCOMING_APP_ALL NUMBER;
--V_INCOMING_APP_URGENT NUMBER;
--V_ON_HAND_WIP NUMBER;
--V_ON_HAND_URGENT NUMBER;
V_SCHEDULE_ID NUMBER;
V_CNT NUMBER := 0;
begin

    SELECT TRUNC(APP_DATE) + (SYSDATE - TRUNC(SYSDATE))
    INTO  V_APPLICATION_DATE
    FROM  APPLICATION_DATE;

     SELECT MAX(SCHEDULE_ID) INTO V_SCHEDULE_ID
     FROM DHB_SCHEDULE;

     DELETE FROM DHB_TEAM WHERE DHB_TYPE = '01' AND DHP_SEQ = 1;

    V_DHB_TYPE := '01';
    V_DHB_GROUP := 1;
    V_DHB_SEQ :=1;

              --POINT_PER_DAY_ACTUAL
              INSERT INTO DHB_TEAM
                (TEAM_ID,SCHEDULE_ID,DHB_TYPE,DHB_GROUP ,DHP_SEQ
                ,POINT_PER_DAY_ACTUAL
                ,CREATE_DATE)
              SELECT MU.TEAM_ID,V_SCHEDULE_ID
                    ,V_DHB_TYPE,V_DHB_GROUP,V_DHB_SEQ
                    ,NVL(SUM(U.TOTAL_POINT),0)
                    ,V_APPLICATION_DATE
              FROM USER_PERFORMANCE U
              JOIN MS_USER_TEAM MU ON U.USER_NAME = MU.USER_ID
              WHERE U.WORKING_DATE BETWEEN TRUNC(V_APPLICATION_DATE) AND (TRUNC(V_APPLICATION_DATE)+0.99999)
              GROUP BY MU.TEAM_ID,V_SCHEDULE_ID
              ,V_DHB_TYPE,V_DHB_GROUP,V_DHB_SEQ,V_APPLICATION_DATE;

              --POINT_PER_DAY_TARGET
              INSERT INTO DHB_TEAM
                (TEAM_ID,SCHEDULE_ID,DHB_TYPE,DHB_GROUP ,DHP_SEQ
                ,POINT_PER_DAY_TARGET
                ,CREATE_DATE)
              SELECT  MU.TEAM_ID,V_SCHEDULE_ID
                      ,V_DHB_TYPE,V_DHB_GROUP,V_DHB_SEQ
                      ,NVL(SUM(U.WORK_TARGET),0)
                      ,V_APPLICATION_DATE
              FROM US_USER_DETAIL U
              JOIN MS_USER_TEAM MU ON U.USER_NAME = MU.USER_ID
              GROUP BY MU.TEAM_ID,V_SCHEDULE_ID
              ,V_DHB_TYPE,V_DHB_GROUP,V_DHB_SEQ,V_APPLICATION_DATE;


              --APP_PER_DAY_ACTUAL
              INSERT INTO DHB_TEAM
                    (TEAM_ID,SCHEDULE_ID,DHB_TYPE,DHB_GROUP ,DHP_SEQ
                    ,APP_PER_DAY_ACTUAL
                    ,CREATE_DATE)
              SELECT MU.TEAM_ID,V_SCHEDULE_ID
                    ,V_DHB_TYPE,V_DHB_GROUP,V_DHB_SEQ
                    ,COUNT ( DISTINCT B.BPD_INSTANCE_ID)
                    ,V_APPLICATION_DATE
               FROM LSW_TASK B
               JOIN LSW_USR_XREF U ON U.USER_ID = B.USER_ID
               JOIN MS_USER_TEAM MU ON U.USER_NAME = MU.USER_ID
               WHERE B.STATUS = '32'
               AND TRUNC(CLOSE_DATETIME) = TRUNC(V_APPLICATION_DATE)
               GROUP BY
                 MU.TEAM_ID,V_SCHEDULE_ID
                 ,V_DHB_TYPE,V_DHB_GROUP,V_DHB_SEQ,V_APPLICATION_DATE;

end P_GEN_DHB_TEAM_DAY;

PROCEDURE P_GEN_DHB_TEAM_WEEK
AS
V_APPLICATION_DATE DATE;
V_DHB_TYPE VARCHAR2(2);
--V_POSITION_LEVEL VARCHAR2(30) := '';
V_DHB_GROUP NUMBER;
V_DHB_SEQ NUMBER;
--V_INPUT NUMBER;
--V_OUTPUT NUMBER;
--V_INCOMING_APP_ALL NUMBER;
--V_INCOMING_APP_URGENT NUMBER;
--V_ON_HAND_WIP NUMBER;
--V_ON_HAND_URGENT NUMBER;
V_SCHEDULE_ID NUMBER;
V_WORKING_DAY NUMBER;
V_START_DAY DATE;
V_END_DAY DATE;

begin

    SELECT TRUNC(APP_DATE) + (SYSDATE - TRUNC(SYSDATE))
    INTO V_APPLICATION_DATE
    FROM  APPLICATION_DATE;

    SELECT MAX(SCHEDULE_ID) INTO V_SCHEDULE_ID
    FROM DHB_SCHEDULE;

    DELETE FROM DHB_TEAM_WEEK;

    SELECT
        COUNT(*),
        MIN(ALL_DATE),
        MAX(ALL_DATE)
    INTO
        V_WORKING_DAY,
        V_START_DAY,
        V_END_DAY
    FROM
        (
            WITH
                T AS
                (
                    SELECT
                        TRUNC(V_APPLICATION_DATE , 'IW') +6 END_DATE,
                        TRUNC(V_APPLICATION_DATE , 'IW') -1 START_DATE
                    FROM DUAL
                )
            SELECT T.START_DATE + LEVEL ALL_DATE
            FROM DUAL, T
            WHERE TO_CHAR(T.START_DATE + LEVEL, 'D') NOT IN (1,7) CONNECT BY T.START_DATE + LEVEL <= T.END_DATE
            MINUS
                ( SELECT  HOLIDAY_DATE FROM MS_HOLIDAY MH ));

V_DHB_TYPE := '01';
V_DHB_GROUP := 1;
V_DHB_SEQ :=2;

              --POINT_PER_WEEK_ACTUAL
              INSERT INTO DHB_TEAM_WEEK
                (TEAM_ID,SCHEDULE_ID,DHB_TYPE,DHB_GROUP ,DHP_SEQ
                ,POINT_PER_WEEK_ACTUAL
                ,CREATE_DATE)
              SELECT MU.TEAM_ID,V_SCHEDULE_ID
                    ,V_DHB_TYPE,V_DHB_GROUP,V_DHB_SEQ
                    ,NVL(SUM(U.TOTAL_POINT),0)
                    ,V_APPLICATION_DATE
              FROM USER_PERFORMANCE U
              JOIN MS_USER_TEAM MU ON U.USER_NAME = MU.USER_ID
              WHERE U.WORKING_DATE BETWEEN TRUNC(V_START_DAY) AND (TRUNC(V_END_DAY)+0.99999)
              GROUP BY MU.TEAM_ID,V_SCHEDULE_ID
              ,V_DHB_TYPE,V_DHB_GROUP,V_DHB_SEQ,V_APPLICATION_DATE;

              --POINT_PER_WEEK_TARGET
              INSERT INTO DHB_TEAM_WEEK
                (TEAM_ID,SCHEDULE_ID,DHB_TYPE,DHB_GROUP ,DHP_SEQ
                ,POINT_PER_WEEK_TARGET
                ,CREATE_DATE)
              SELECT  MU.TEAM_ID,V_SCHEDULE_ID
                      ,V_DHB_TYPE,V_DHB_GROUP,V_DHB_SEQ
                      ,NVL(SUM(U.WORK_TARGET),0)
                      ,V_APPLICATION_DATE
              FROM US_USER_DETAIL U
              JOIN MS_USER_TEAM MU ON U.USER_NAME = MU.USER_ID
              GROUP BY MU.TEAM_ID,V_SCHEDULE_ID
              ,V_DHB_TYPE,V_DHB_GROUP,V_DHB_SEQ,V_APPLICATION_DATE;

              --APP_PER_WEEK_ACTUAL
              INSERT INTO DHB_TEAM_WEEK
                    (TEAM_ID,SCHEDULE_ID,DHB_TYPE,DHB_GROUP ,DHP_SEQ
                    ,APP_PER_WEEK_ACTUAL
                    ,CREATE_DATE)
              SELECT MU.TEAM_ID,V_SCHEDULE_ID
                    ,V_DHB_TYPE,V_DHB_GROUP,V_DHB_SEQ
                    ,COUNT ( DISTINCT B.BPD_INSTANCE_ID)
                    ,V_APPLICATION_DATE
               FROM LSW_TASK B
               JOIN LSW_USR_XREF U ON U.USER_ID = B.USER_ID
               JOIN MS_USER_TEAM MU ON U.USER_NAME = MU.USER_ID
               WHERE B.STATUS = '32'
               AND TRUNC(CLOSE_DATETIME) BETWEEN TRUNC(V_START_DAY) AND TRUNC(V_END_DAY)
               GROUP BY
                 MU.TEAM_ID,V_SCHEDULE_ID
                 ,V_DHB_TYPE,V_DHB_GROUP,V_DHB_SEQ,V_APPLICATION_DATE;

              --APP_PER_WEEK_TARGET

END P_GEN_DHB_TEAM_WEEK;

PROCEDURE P_GEN_DHB_TEAM_MONTH
AS
V_APPLICATION_DATE DATE;
V_DHB_TYPE VARCHAR2(2) := '02';
--V_POSITION_LEVEL VARCHAR2(30) := '';
V_DBH_GROUP NUMBER;
V_DBH_SEQ NUMBER;
--V_INPUT NUMBER;
--V_OUTPUT NUMBER;
--V_INCOMING_APP_ALL NUMBER;
--V_INCOMING_APP_URGENT NUMBER;
--V_ON_HAND_WIP NUMBER;
--V_ON_HAND_URGENT NUMBER;
V_SCHEDULE_ID NUMBER;
V_CNT NUMBER := 0;
begin

    SELECT TRUNC(APP_DATE) + (SYSDATE - TRUNC(SYSDATE))
    INTO V_APPLICATION_DATE
    FROM  APPLICATION_DATE;

    SELECT MAX(SCHEDULE_ID) INTO V_SCHEDULE_ID
    FROM DHB_SCHEDULE;

    DELETE FROM DHB_TEAM_MONTH;

--OUTPUT
V_DHB_TYPE := '04';
V_DBH_GROUP := 1;
V_DBH_SEQ :=1;

              INSERT INTO DHB_TEAM_MONTH
              (TEAM_ID,SCHEDULE_ID,MONTH,DHB_TYPE,DHB_GROUP ,DHP_SEQ
              ,OUTPUT
              ,CREATE_DATE)
              SELECT MU.TEAM_ID,V_SCHEDULE_ID
                    ,TO_CHAR(LT.CLOSE_DATETIME,'YYYYMM')
                    ,V_DHB_TYPE,V_DBH_GROUP,V_DBH_SEQ
                    ,COUNT(DISTINCT LT.BPD_INSTANCE_ID)
                    ,V_APPLICATION_DATE
              --FROM ORIG_APPLICATION_GROUP OG
              --JOIN LSW_TASK LT ON LT.BPD_INSTANCE_ID = OG.INSTANT_ID
              FROM LSW_TASK LT
              JOIN LSW_USR_XREF LU ON LU.USER_ID = LT.USER_ID
              JOIN MS_USER_TEAM MU ON LU.USER_NAME = MU.USER_ID
              WHERE LT.CLOSE_DATETIME BETWEEN TRUNC(V_APPLICATION_DATE,'MM')  AND V_APPLICATION_DATE
              AND LT.STATUS = '32'
              GROUP BY MU.TEAM_ID,V_SCHEDULE_ID,TO_CHAR(LT.CLOSE_DATETIME,'YYYYMM')
              ,V_DHB_TYPE,V_DBH_GROUP,V_DBH_SEQ,V_APPLICATION_DATE;

              INSERT INTO DHB_TEAM_MONTH
              (TEAM_ID,SCHEDULE_ID,MONTH,DHB_TYPE,DHB_GROUP ,DHP_SEQ
              ,TEAM_ERROR_CAPTURED
              ,CREATE_DATE)
              SELECT MU.TEAM_ID,V_SCHEDULE_ID
                    ,TO_CHAR(LT.CLOSE_DATETIME,'YYYYMM')
                    ,V_DHB_TYPE,V_DBH_GROUP,V_DBH_SEQ
                    ,COUNT(DISTINCT LT.BPD_INSTANCE_ID)
                    ,V_APPLICATION_DATE
              FROM ORIG_APPLICATION_GROUP OG
              JOIN LSW_TASK LT ON LT.BPD_INSTANCE_ID = OG.INSTANT_ID
              JOIN LSW_USR_XREF LU ON LU.USER_ID = LT.USER_ID
              JOIN MS_USER_TEAM MU ON LU.USER_NAME = MU.USER_ID
              WHERE LT.CLOSE_DATETIME BETWEEN TRUNC(V_APPLICATION_DATE,'MM')  AND V_APPLICATION_DATE
              AND LT.STATUS = '32'
              AND ( EXISTS( SELECT 1
                            FROM ORIG_APPLICATION_GROUP OG
                            JOIN ORIG_COMPARISON_DATA OCD ON OCD.APPLICATION_GROUP_ID = OG.APPLICATION_GROUP_ID
                            WHERE OG.INSTANT_ID = LT.BPD_INSTANCE_ID
                            AND OCD.COMPARE_FLAG = 'W'
                            AND OCD.SRC_OF_DATA = '2MAKER'
                            AND TRUNC(OCD.UPDATE_DATE, 'MONTH') = TRUNC(V_APPLICATION_DATE,'MONTH')
                            AND MU.USER_ID = OCD.CREATE_BY
                          )
               OR  EXISTS (SELECT  1
                        FROM ORIG_APPLICATION_GROUP OG
                        JOIN ORIG_PERSONAL_INFO OP ON OP.APPLICATION_GROUP_ID = OG.APPLICATION_GROUP_ID
                        JOIN INC_INFO INC ON INC.PERSONAL_ID = OP.PERSONAL_ID
                        WHERE OG.INSTANT_ID = LT.BPD_INSTANCE_ID
                        AND INC.COMPARE_FLAG = 'W'
                        AND INC.CREATE_BY = MU.USER_ID ) )
              GROUP BY MU.TEAM_ID,V_SCHEDULE_ID,TO_CHAR(LT.CLOSE_DATETIME,'YYYYMM')
              ,V_DHB_TYPE,V_DBH_GROUP,V_DBH_SEQ,V_APPLICATION_DATE;


end;

PROCEDURE P_GEN_DHB_ROLE
AS
V_APPLICATION_DATE DATE;
V_DHB_TYPE VARCHAR2(2) := '07';
V_DBH_GROUP NUMBER:= 1;
V_DBH_SEQ NUMBER;
V_ROLE_NAME VARCHAR2(100);
V_ROLE_DISPLAY VARCHAR2(100);

V_NUMBER_APP_OUT_STATION NUMBER;
V_NUMBER_APP_OVER_STANDARD NUMBER;
V_ARCHIEVEMENT_OLA NUMBER;

V_WORKING_HR_END_KBANK VARCHAR2(5);
V_WORKING_HR_END_PHR VARCHAR2(5);
V_WORKING_HR_START_KBANK VARCHAR2(5);
V_WORKING_HR_START_PHR VARCHAR2(5);
V_SCHEDULE_ID NUMBER;

BEGIN

    SELECT TRUNC(APP_DATE) + (SYSDATE - TRUNC(SYSDATE))
    INTO V_APPLICATION_DATE
    FROM  APPLICATION_DATE;

    SELECT MAX(SCHEDULE_ID) INTO V_SCHEDULE_ID
    FROM DHB_SCHEDULE;

    DELETE FROM DHB_ROLE;

    SELECT VALUE1
    INTO V_WORKING_HR_END_KBANK
    FROM WORKFLOW_PARAM
    WHERE PARAM_CODE = 'WORKING_HR_END_KBANK';

    SELECT  VALUE1
    INTO  V_WORKING_HR_END_PHR
    FROM  WORKFLOW_PARAM
    WHERE PARAM_CODE = 'WORKING_HR_END_PHR';

    SELECT VALUE1
    INTO V_WORKING_HR_START_KBANK
    FROM WORKFLOW_PARAM
    WHERE PARAM_CODE = 'WORKING_HR_START_KBANK';

    SELECT VALUE1
    INTO V_WORKING_HR_START_PHR
    FROM WORKFLOW_PARAM
    WHERE PARAM_CODE = 'WORKING_HR_START_PHR';

    FOR V_DBH_SEQ IN 1..8
    LOOP
        CASE (V_DBH_SEQ)
        WHEN 1 THEN
            V_ROLE_NAME := 'IA';
            V_ROLE_DISPLAY := 'IA';
        WHEN 2 THEN
            V_ROLE_NAME := 'DE1_1';
            V_ROLE_DISPLAY := 'DE1.1';
        WHEN 3 THEN
            V_ROLE_NAME := 'DE1_2';
            V_ROLE_DISPLAY := 'DE1.2';
        WHEN 4 THEN
            V_ROLE_NAME := 'DV';
            V_ROLE_DISPLAY := 'DV';
        WHEN 5 THEN
            V_ROLE_NAME := 'VT';
            V_ROLE_DISPLAY := 'VT';
        WHEN 6 THEN
            V_ROLE_NAME := 'CA';
            V_ROLE_DISPLAY := 'CA';
        WHEN 7 THEN
            V_ROLE_NAME := 'DE2';
            V_ROLE_DISPLAY := 'DE2';
        WHEN 8 THEN
            V_ROLE_NAME := 'FU';
            V_ROLE_DISPLAY := 'FU';
        END CASE;

        SELECT COUNT( DISTINCT LT.BPD_INSTANCE_ID)
            INTO
                V_NUMBER_APP_OUT_STATION
            FROM
                LSW_TASK LT
            JOIN
                LSW_USR_XREF LU
            ON
                LU.USER_ID = LT.USER_ID
            JOIN
                ORIG_IAS.USER_ROLE UR
            ON
                UR.USER_NAME = LU.USER_NAME
            JOIN
                MS_USER_TEAM U
            ON
                UR.USER_NAME = U.USER_ID
            JOIN
                ORIG_IAS.ROLE R
            ON
                R.ROLE_ID = UR.ROLE_ID
            AND R.ROLE_NAME = V_ROLE_NAME
            WHERE
                LT.STATUS = '32'
            AND CLOSE_DATETIME BETWEEN  TRUNC(V_APPLICATION_DATE,'MM') AND V_APPLICATION_DATE
            AND EXISTS( SELECT 1 FROM ORIG_APPLICATION_GROUP OG
                    WHERE OG.INSTANT_ID = LT.BPD_INSTANCE_ID
                    AND OG.JOB_STATE IN ('NM9902',
                                         'NM9903',
                                         'NM9904') )
                /*
                AND EXISTS(SELECT 1 FROM
                (
                SELECT T5.TEAM_ID T5 ,MT5.POSITION_ID,MT5.USER_ID ,T4.TEAM_ID T4,T3.TEAM_ID T3 ,T2.TEAM_ID T2,
                T1.TEAM_ID T1
                FROM MS_TEAM T5
                JOIN MS_USER_TEAM MT5 ON T5.TEAM_ID = MT5.TEAM_ID
                LEFT JOIN MS_TEAM T4 ON T4.UNDER = T5.TEAM_ID
                LEFT JOIN MS_TEAM T3 ON T3.UNDER = T4.TEAM_ID
                LEFT JOIN MS_TEAM T2 ON T2.UNDER = T3.TEAM_ID
                LEFT JOIN MS_TEAM T1 ON T1.UNDER = T2.TEAM_ID ) T WHERE  (U.TEAM_ID  = T.T5 OR U.TEAM_ID = T4
                OR U.TEAM_ID = T3 OR U.TEAM_ID = T2 OR U.TEAM_ID = T1)
                AND T.USER_ID = CUR.USER_ID)*/
                ;

           SELECT
                COUNT( DISTINCT LT.BPD_INSTANCE_ID)
            INTO
                V_NUMBER_APP_OVER_STANDARD
            FROM
                LSW_TASK LT
            JOIN
                LSW_USR_XREF LU
            ON
                LU.USER_ID = LT.USER_ID
            JOIN
                ORIG_IAS.USER_ROLE UR
            ON
                UR.USER_NAME = LU.USER_NAME
            JOIN
                MS_USER_TEAM U
            ON
                UR.USER_NAME = U.USER_ID
            JOIN
                ORIG_IAS.ROLE R
            ON
                R.ROLE_ID = UR.ROLE_ID
            AND R.ROLE_NAME = V_ROLE_NAME
            JOIN
                MS_PRE_TIME MP
            ON
                MP.ROLE_NAME= R.ROLE_NAME
            LEFT JOIN
                (
                    SELECT
                        SUBSTR (CSV, INSTR (CSV, ',', 1, LEV) + 1, INSTR (CSV, ',', 1, LEV + 1) - INSTR (CSV,
                        ',', 1, LEV) - 1) ROLE_NAME
                    FROM
                        (
                            SELECT
                                ',' || VALUE1 || ',' CSV
                            FROM
                                REPORT_PARAM
                            WHERE
                                PARAM_TYPE = 'STAFF_KBANK_ROLE' ),
                        (
                            SELECT
                                LEVEL LEV
                            FROM
                                DUAL CONNECT BY LEVEL <= 100)
                    WHERE
                        LEV <= LENGTH (CSV) - LENGTH (REPLACE (CSV, ',')) - 1) KBANK
            ON
                KBANK.ROLE_NAME = R.ROLE_NAME
            WHERE
                LT.STATUS = '32'
            AND CLOSE_DATETIME BETWEEN  TRUNC(V_APPLICATION_DATE,'MM') AND V_APPLICATION_DATE
            AND (
                    SELECT
                        SUM(GET_BUSINESS_TIME_BETWEEN(LT.READ_DATETIME,LT.CLOSE_DATETIME ,DECODE
                        (KBANK.ROLE_NAME,NULL,V_WORKING_HR_START_PHR,V_WORKING_HR_START_KBANK) ,DECODE
                        (KBANK.ROLE_NAME,NULL,V_WORKING_HR_END_PHR,V_WORKING_HR_END_KBANK)) )
                    FROM
                        LSW_TASK X
                    WHERE
                        X.BPD_INSTANCE_ID = LT.BPD_INSTANCE_ID
                    AND X.ACTIVITY_NAME = LT.ACTIVITY_NAME) > MP.PRE_TIME
            AND EXISTS
                (
                    SELECT
                        1
                    FROM
                        ORIG_APPLICATION_GROUP OG
                    WHERE
                        OG.INSTANT_ID = LT.BPD_INSTANCE_ID
                    AND OG.JOB_STATE IN ('NM9902',
                                         'NM9903',
                                         'NM9904') )
                /*
                and exists(select 1 from
                (
                select t5.team_id t5 ,mt5.position_id,mt5.user_id ,t4.team_id t4,t3.team_id t3 ,t2.team_id t2,
                t1.team_id t1
                from ms_team t5
                join ms_user_team mt5 on t5.team_id = mt5.team_id
                left join ms_team t4 on t4.under = t5.team_id
                left join ms_team t3 on t3.under = t4.team_id
                left join ms_team t2 on t2.under = t3.team_id
                left join ms_team t1 on t1.under = t2.team_id ) t where  (u.team_id  = t.t5 or u.team_id = t4
                or u.team_id = t3 or u.team_id = t2 or u.team_id = t1)
                and t.user_id = cur.user_id)*/
                ;

            IF V_NUMBER_APP_OUT_STATION > 0 THEN

                V_ARCHIEVEMENT_OLA := (V_NUMBER_APP_OUT_STATION - V_NUMBER_APP_OVER_STANDARD)/
                V_NUMBER_APP_OUT_STATION * 100;
            ELSE
                V_ARCHIEVEMENT_OLA := 0;
            END IF;

            INSERT INTO DHB_ROLE
             ( ROLE_NAME
              ,ROLE_DISPLAY
              ,SCHEDULE_ID
              ,DHB_TYPE
              ,DHB_GROUP
              ,DHP_SEQ
              ,NUMBER_APP_OUT_STATION
              ,NUMBER_APP_OVER_STANDARD
              ,ARCHIEVEMENT_OLA
              ,CREATE_DATE)
            VALUES
              (V_ROLE_NAME
              ,V_ROLE_DISPLAY
              ,V_SCHEDULE_ID
              ,V_DHB_TYPE
              ,V_DBH_GROUP
              ,V_DBH_SEQ
              ,V_NUMBER_APP_OUT_STATION
              ,V_NUMBER_APP_OVER_STANDARD
              ,V_ARCHIEVEMENT_OLA
              ,V_APPLICATION_DATE);


    END LOOP;

END;

PROCEDURE P_GEN_DASHBOARD_01
AS
    V_DHB_TYPE VARCHAR2(2) := '01';
    V_POSITION_LEVEL VARCHAR2(30) := 'ALL';
    V_DHB_OWNER VARCHAR2(30) := 'ALL';
    V_DHB_GROUP NUMBER;
    V_DHB_SEQ NUMBER;
    V_SCHEDULE_ID NUMBER;
    V_SUMMARY_ID NUMBER;
    V_POINT_PER_DAY_ACTUAL NUMBER;
    V_POINT_PER_DAY_TARGET NUMBER;
    V_APP_PER_DAY_ACTUAL NUMBER;
    V_POINT_PER_WEEK_ACTUAL NUMBER;
    V_POINT_PER_WEEK_TARGET NUMBER;
    V_APP_PER_WEEK_ACTUAL NUMBER;
    V_KBANK_ROLE VARCHAR2(100);
    V_KBANK_HOURS NUMBER;
    V_PHR_HOURS NUMBER;
    V_SUM_HOURS NUMBER;
    V_WORKING_DAY NUMBER;
    V_START_DAY DATE;
    V_END_DAY DATE;
    V_APPLICATION_DATE DATE;
    V_HOLIDAY NUMBER;
BEGIN

    SELECT MAX(SCHEDULE_ID) INTO V_SCHEDULE_ID
    FROM DHB_SCHEDULE;
    -- ALL --
    --DAY --
    SELECT DHB_SUMMARY_DATA_PK.NEXTVAL
    INTO V_SUMMARY_ID
    FROM  DUAL ;

    SELECT TRUNC(APP_DATE)
    INTO V_APPLICATION_DATE
    FROM APPLICATION_DATE;

    SELECT
        COUNT(*),
        MIN(ALL_DATE),
        MAX(ALL_DATE)
    INTO
        V_WORKING_DAY,
        V_START_DAY,
        V_END_DAY
    FROM
        (
            WITH
                T AS
                (
                    SELECT
                        TRUNC(V_APPLICATION_DATE , 'IW') +6 END_DATE,
                        TRUNC(V_APPLICATION_DATE , 'IW') -1 START_DATE
                    FROM DUAL
                )
            SELECT T.START_DATE + LEVEL ALL_DATE
            FROM DUAL, T
            WHERE TO_CHAR(T.START_DATE + LEVEL, 'D') NOT IN (1,7) CONNECT BY T.START_DATE + LEVEL <= T.END_DATE
            MINUS
                ( SELECT  HOLIDAY_DATE FROM MS_HOLIDAY MH ));

    SELECT COUNT(*)
    INTO V_HOLIDAY
    FROM  DUAL
    WHERE TO_CHAR(V_APPLICATION_DATE,'D') IN (1,7)
    OR  EXISTS
        (
          SELECT  1
          FROM MS_HOLIDAY M
          WHERE TRUNC(M.HOLIDAY_DATE)=TRUNC(V_APPLICATION_DATE)
          );

    --TEAM PERFORMANCE --
    V_DHB_TYPE := '01';
    V_POSITION_LEVEL := 'ALL';
    V_DHB_OWNER := 'ALL';
    V_DHB_GROUP := 1;
    V_DHB_SEQ :=1;

    SELECT NVL(SUM(USER_PERFORMANCE.TOTAL_POINT),0)
    INTO V_POINT_PER_DAY_ACTUAL
    FROM USER_PERFORMANCE
    WHERE WORKING_DATE BETWEEN TRUNC(V_APPLICATION_DATE) AND (TRUNC(V_APPLICATION_DATE)+0.99999);


    SELECT NVL(SUM(US_USER_DETAIL.WORK_TARGET),0)
    INTO V_POINT_PER_DAY_TARGET
    FROM US_USER_DETAIL;

    SELECT  COUNT ( DISTINCT B.BPD_INSTANCE_ID)
    INTO V_APP_PER_DAY_ACTUAL
    FROM  LSW_TASK B
        --JOIN LSW_USR_XREF U ON U.USER_ID = B.USER_ID
        --JOIN US_USER_DETAIL UD ON UD.USER_NAME = U.USER_NAME
    WHERE B.STATUS = '32'
    AND CLOSE_DATETIME BETWEEN TRUNC(V_APPLICATION_DATE) AND V_APPLICATION_DATE ;

    SELECT VALUE1
    INTO V_KBANK_ROLE
    FROM WORKFLOW_PARAM
    WHERE PARAM_CODE = 'STAFF_KBANK_ROLE';

    SELECT
        24*(
        (
            SELECT TO_DATE(VALUE1,'HH24:MI')
            FROM WORKFLOW_PARAM
            WHERE PARAM_CODE = 'WORKING_HR_END_KBANK') -
        (
            SELECT TO_DATE(VALUE1,'HH24:MI')
            FROM WORKFLOW_PARAM
            WHERE PARAM_CODE = 'WORKING_HR_START_KBANK'))
    INTO V_KBANK_HOURS
    FROM DUAL;

    SELECT
        24*(
        (
            SELECT TO_DATE(VALUE1,'HH24:MI')
            FROM WORKFLOW_PARAM
            WHERE PARAM_CODE = 'WORKING_HR_END_PHR') -
        (
            SELECT TO_DATE(VALUE1,'HH24:MI')
            FROM WORKFLOW_PARAM
            WHERE PARAM_CODE = 'WORKING_HR_START_PHR'))
    INTO V_PHR_HOURS
    FROM DUAL;

    SELECT NVL(SUM( V_KBANK_HOURS * RP.VALUE1),0)
    INTO V_SUM_HOURS
    FROM ORIG_IAS.USER_ROLE UR
    JOIN ORIG_IAS.ROLE R
    ON R.ROLE_ID = UR.ROLE_ID
    AND PKA_DASHBOARD.F_CHECK_ROLE(R.ROLE_NAME ,V_KBANK_ROLE) = 1
    JOIN REPORT_PARAM RP
    ON RP.PARAM_TYPE = 'BASELINE_APP'
    AND PARAM_CODE = R.ROLE_NAME
    WHERE EXISTS ( SELECT 1
                   FROM MS_USER_TEAM M
                  WHERE UR.USER_NAME = M.USER_ID) ;

    SELECT NVL(SUM( V_PHR_HOURS * RP.VALUE1),0) + V_SUM_HOURS
    INTO V_SUM_HOURS
    FROM ORIG_IAS.USER_ROLE UR
    JOIN ORIG_IAS.ROLE R ON R.ROLE_ID = UR.ROLE_ID
                        AND PKA_DASHBOARD.F_CHECK_ROLE(R.ROLE_NAME ,V_KBANK_ROLE) = 0
    JOIN  REPORT_PARAM RP ON RP.PARAM_TYPE = 'BASELINE_APP'
    AND PARAM_CODE = R.ROLE_NAME
    WHERE  EXISTS( SELECT  1
                  FROM MS_USER_TEAM M
                  WHERE UR.USER_NAME = M.USER_ID) ;

    IF V_HOLIDAY = 1 THEN
        V_POINT_PER_DAY_TARGET := 0;
    V_SUM_HOURS := 0;
    END IF;

    /* no need to delete data in procedure
    DELETE
    FROM
        DHB_SUMMARY_DATA
    WHERE
        DHB_TYPE = V_DHB_TYPE; */

    INSERT INTO DHB_SUMMARY_DATA
        (
            SUMMARY_ID,
            POSITION_LEVEL ,
            DHB_TYPE ,
            DHB_OWNER ,
            DHB_GROUP,
            DHP_SEQ,
            VALUE1 ,
            DESCRIPTION1,
            VALUE2,
            DESCRIPTION2 ,
            VALUE3,
            DESCRIPTION3 ,
            VALUE4 ,
            DESCRIPTION4,
            CREATE_DATE,
            STATUS
        )
        VALUES
        (
            V_SUMMARY_ID,
            V_POSITION_LEVEL,
            V_DHB_TYPE,
            V_DHB_OWNER,
            V_DHB_GROUP,
            V_DHB_SEQ,
            V_POINT_PER_DAY_ACTUAL,
            'POINT PER DAY ACTUAL',
            V_POINT_PER_DAY_TARGET,
            'POINT PER DAY TARGET',
            V_APP_PER_DAY_ACTUAL,
            'APP PER DAY ACTUAL',
            V_SUM_HOURS,
            'APP PER DAY TARGET',
            V_APPLICATION_DATE,
            'W'
        );
    COMMIT;

    --WEEK --
    SELECT DHB_SUMMARY_DATA_PK.NEXTVAL
    INTO V_SUMMARY_ID
    FROM DUAL ;

    V_DHB_TYPE := '01';
    V_POSITION_LEVEL := 'ALL';
    V_DHB_OWNER := 'ALL';
    V_DHB_GROUP := 1;
    V_DHB_SEQ :=2;
    --TEAM PERFORMANCE --

    SELECT  NVL(SUM(USER_PERFORMANCE.TOTAL_POINT),0)
    INTO V_POINT_PER_DAY_ACTUAL
    FROM USER_PERFORMANCE
    WHERE TRUNC(WORKING_DATE) BETWEEN TRUNC(V_START_DAY) AND TRUNC(V_END_DAY);

    SELECT  NVL(SUM(US_USER_DETAIL.WORK_TARGET),0) * V_WORKING_DAY
    INTO V_POINT_PER_DAY_TARGET
    FROM US_USER_DETAIL;

    SELECT COUNT ( DISTINCT B.BPD_INSTANCE_ID)
    INTO V_APP_PER_DAY_ACTUAL
    FROM LSW_TASK B
    JOIN LSW_USR_XREF U ON U.USER_ID = B.USER_ID
    JOIN US_USER_DETAIL UD ON UD.USER_NAME = U.USER_NAME
    WHERE B.STATUS = '32'
    AND TRUNC(CLOSE_DATETIME) BETWEEN TRUNC(V_START_DAY) AND TRUNC(V_END_DAY);

    SELECT  VALUE1
    INTO V_KBANK_ROLE
    FROM WORKFLOW_PARAM
    WHERE PARAM_CODE = 'STAFF_KBANK_ROLE';

    SELECT
        24*(
        (
            SELECT TO_DATE(VALUE1,'HH24:MI')
            FROM WORKFLOW_PARAM
            WHERE PARAM_CODE = 'WORKING_HR_END_KBANK') -
        (
            SELECT TO_DATE(VALUE1,'HH24:MI')
            FROM WORKFLOW_PARAM
            WHERE PARAM_CODE = 'WORKING_HR_START_KBANK'))
    INTO V_KBANK_HOURS
    FROM DUAL;

    SELECT
        24*(
        (
            SELECT TO_DATE(VALUE1,'HH24:MI')
            FROM WORKFLOW_PARAM
            WHERE PARAM_CODE = 'WORKING_HR_END_PHR') -
        (
            SELECT TO_DATE(VALUE1,'HH24:MI')
            FROM WORKFLOW_PARAM
            WHERE PARAM_CODE = 'WORKING_HR_START_PHR'))
    INTO V_PHR_HOURS
    FROM DUAL;

    SELECT NVL(SUM( NVL(V_KBANK_HOURS,0) * NVL(RP.VALUE1,0)* NVL(V_WORKING_DAY,0)) ,0)
    INTO V_SUM_HOURS
    FROM  ORIG_IAS.USER_ROLE UR
    JOIN ORIG_IAS.ROLE R ON  R.ROLE_ID = UR.ROLE_ID
    AND PKA_DASHBOARD.F_CHECK_ROLE(R.ROLE_NAME ,V_KBANK_ROLE) = 1
    JOIN REPORT_PARAM RP
    ON RP.PARAM_TYPE = 'BASELINE_APP'
    AND PARAM_CODE = R.ROLE_NAME
    WHERE EXISTS( SELECT 1
                  FROM MS_USER_TEAM M
                  WHERE UR.USER_NAME = M.USER_ID) ;

    SELECT
        NVL(SUM( NVL(V_PHR_HOURS,0) * NVL(RP.VALUE1,0)* NVL(V_WORKING_DAY,0)),0) + V_SUM_HOURS
    INTO V_SUM_HOURS
    FROM ORIG_IAS.USER_ROLE UR
    JOIN ORIG_IAS.ROLE R ON R.ROLE_ID = UR.ROLE_ID
                         AND PKA_DASHBOARD.F_CHECK_ROLE(R.ROLE_NAME ,V_KBANK_ROLE) = 0
    JOIN REPORT_PARAM RP ON RP.PARAM_TYPE = 'BASELINE_APP'
    AND PARAM_CODE = R.ROLE_NAME
    WHERE EXISTS ( SELECT 1
                   FROM MS_USER_TEAM M
                   WHERE UR.USER_NAME = M.USER_ID) ;


    INSERT
    INTO
        DHB_SUMMARY_DATA
        (
            SUMMARY_ID,
            POSITION_LEVEL ,
            DHB_TYPE ,
            DHB_OWNER ,
            DHB_GROUP,
            DHP_SEQ,
            VALUE1 ,
            DESCRIPTION1,
            VALUE2,
            DESCRIPTION2 ,
            VALUE3,
            DESCRIPTION3 ,
            VALUE4 ,
            DESCRIPTION4,
            CREATE_DATE,
            STATUS
        )
        VALUES
        (
            V_SUMMARY_ID,
            V_POSITION_LEVEL,
            V_DHB_TYPE,
            V_DHB_OWNER,
            V_DHB_GROUP,
            V_DHB_SEQ,
            V_POINT_PER_DAY_ACTUAL,
            'Point per week actual',
            V_POINT_PER_DAY_TARGET,
            'Point per week target',
            V_APP_PER_DAY_ACTUAL ,
            'App per week actual',
            V_SUM_HOURS,
            'App per week target',
            V_APPLICATION_DATE,
            'W'
        );
    COMMIT;

    -- BY USER  MANAGER ROLE --
    FOR CUR IN  ( SELECT DISTINCT
                    USER_ID,
                    (POSITION_ID) POSITION_ID
                FROM   MS_USER_TEAM
                WHERE  POSITION_ID IN (1,2,3,4))
    LOOP
        SELECT DHB_SUMMARY_DATA_PK.NEXTVAL
        INTO V_SUMMARY_ID
        FROM DUAL ;

    --TEAM PERFORMANCE --
    V_DHB_TYPE := '01';
    V_POSITION_LEVEL :=CUR.POSITION_ID;
    V_DHB_OWNER := CUR.USER_ID;
    V_DHB_GROUP := 1;
    V_DHB_SEQ :=1;

     SELECT SUM(NVL(POINT_PER_DAY_ACTUAL,0))
      INTO V_POINT_PER_DAY_ACTUAL
      FROM DHB_TEAM
      --WHERE SCHEDULE_ID = V_SCHEDULE_ID
      WHERE  DHB_TYPE = V_DHB_TYPE
      AND DHP_SEQ = V_DHB_SEQ
      AND EXISTS (SELECT 1 FROM  MS_USER_TEAM MU
                  JOIN
                      ( SELECT  T5.TEAM_ID T5 ,
                              MT5.POSITION_ID,
                              MT5.USER_ID ,
                              T4.TEAM_ID T4,
                              T3.TEAM_ID T3 ,
                              T2.TEAM_ID T2,
                              T1.TEAM_ID T1
                          FROM  MS_TEAM T5
                          JOIN MS_USER_TEAM MT5 ON  T5.TEAM_ID = MT5.TEAM_ID
                          LEFT JOIN  MS_TEAM T4 ON  T4.TEAM_ID = T5.UNDER
                          LEFT JOIN  MS_TEAM T3 ON T3.TEAM_ID = T4.UNDER
                          LEFT JOIN MS_TEAM T2  ON T2.TEAM_ID = T3.UNDER
                          LEFT JOIN   MS_TEAM T1 ON T1.TEAM_ID = T2.UNDER ) T
                  ON
                      ( MU.TEAM_ID = T.T5
                      OR  MU.TEAM_ID = T4
                      OR  MU.TEAM_ID = T3
                      OR  MU.TEAM_ID = T2
                      OR  MU.TEAM_ID = T1)
                 WHERE MU.USER_ID =  CUR.USER_ID
                 --AND MU.TEAM_ID = DHB_TEAM.TEAM_ID);
                 AND T.T5 = DHB_TEAM.TEAM_ID);

/*
    SELECT NVL(SUM(U.TOTAL_POINT),0)
    INTO V_POINT_PER_DAY_ACTUAL
    FROM USER_PERFORMANCE U
    WHERE  WORKING_DATE BETWEEN TRUNC(V_APPLICATION_DATE) AND (TRUNC(V_APPLICATION_DATE)+0.99999)
    AND EXISTS ( SELECT 1
                  FROM MS_USER_TEAM MU
                  WHERE MU.USER_ID = U.USER_NAME
                  AND MU.TEAM_ID IN
              (
                    SELECT A.TEAM_ID
                    FROM MS_TEAM A
                    LEFT JOIN MS_USER_TEAM MUT ON MUT.TEAM_ID = A.TEAM_ID
                    LEFT JOIN
                        (
                            SELECT
                                T5.TEAM_ID T5 ,
                                MT5.POSITION_ID,
                                MT5.USER_ID ,
                                T4.TEAM_ID T4,
                                T3.TEAM_ID T3 ,
                                T2.TEAM_ID T2,
                                T1.TEAM_ID T1
                            FROM
                                MS_TEAM T5
                            JOIN
                                MS_USER_TEAM MT5
                            ON
                                T5.TEAM_ID = MT5.TEAM_ID
                            LEFT JOIN
                                MS_TEAM T4
                            ON
                                T4.UNDER = T5.TEAM_ID
                            LEFT JOIN
                                MS_TEAM T3
                            ON
                                T3.UNDER = T4.TEAM_ID
                            LEFT JOIN
                                MS_TEAM T2
                            ON
                                T2.UNDER = T3.TEAM_ID
                            LEFT JOIN
                                MS_TEAM T1
                            ON
                                T1.UNDER = T2.TEAM_ID ) T
                    ON
                        (
                            A.TEAM_ID = T.T5
                        OR  A.TEAM_ID = T4
                        OR  A.TEAM_ID = T3
                        OR  A.TEAM_ID = T2
                        OR  A.TEAM_ID = T1)
                    WHERE
                        T.USER_ID = CUR.USER_ID)) ;
*/

      SELECT SUM(NVL(POINT_PER_DAY_TARGET,0))
      INTO V_POINT_PER_DAY_TARGET
      FROM DHB_TEAM
      --WHERE SCHEDULE_ID = V_SCHEDULE_ID
      WHERE DHB_TYPE = V_DHB_TYPE
      AND DHP_SEQ = V_DHB_SEQ
      AND EXISTS (SELECT 1 FROM  MS_USER_TEAM MU
                  JOIN
                      ( SELECT  T5.TEAM_ID T5 ,
                              MT5.POSITION_ID,
                              MT5.USER_ID ,
                              T4.TEAM_ID T4,
                              T3.TEAM_ID T3 ,
                              T2.TEAM_ID T2,
                              T1.TEAM_ID T1
                          FROM  MS_TEAM T5
                          JOIN MS_USER_TEAM MT5 ON  T5.TEAM_ID = MT5.TEAM_ID
                          LEFT JOIN  MS_TEAM T4 ON  T4.TEAM_ID = T5.UNDER
                          LEFT JOIN  MS_TEAM T3 ON T3.TEAM_ID = T4.UNDER
                          LEFT JOIN MS_TEAM T2  ON T2.TEAM_ID = T3.UNDER
                          LEFT JOIN   MS_TEAM T1 ON T1.TEAM_ID = T2.UNDER ) T
                  ON
                      ( MU.TEAM_ID = T.T5
                      OR  MU.TEAM_ID = T4
                      OR  MU.TEAM_ID = T3
                      OR  MU.TEAM_ID = T2
                      OR  MU.TEAM_ID = T1)
                 WHERE MU.USER_ID =  CUR.USER_ID
                 --AND MU.TEAM_ID = DHB_TEAM.TEAM_ID);
                 AND T.T5 = DHB_TEAM.TEAM_ID);

/*
    SELECT  NVL(SUM(U.WORK_TARGET),0)
    INTO V_POINT_PER_DAY_TARGET
    FROM US_USER_DETAIL U
    WHERE EXISTS
        (  SELECT  1
            FROM MS_USER_TEAM MU
            WHERE MU.USER_ID = U.USER_NAME
            AND MU.TEAM_ID IN
                (
                    SELECT
                        A.TEAM_ID
                    FROM
                        MS_TEAM A
                    LEFT JOIN
                        MS_USER_TEAM MUT
                    ON
                        MUT.TEAM_ID = A.TEAM_ID
                    LEFT JOIN
                        (
                            SELECT
                                T5.TEAM_ID T5 ,
                                MT5.POSITION_ID,
                                MT5.USER_ID ,
                                T4.TEAM_ID T4,
                                T3.TEAM_ID T3 ,
                                T2.TEAM_ID T2,
                                T1.TEAM_ID T1
                            FROM
                                MS_TEAM T5
                            JOIN
                                MS_USER_TEAM MT5
                            ON
                                T5.TEAM_ID = MT5.TEAM_ID
                            LEFT JOIN
                                MS_TEAM T4
                            ON
                                T4.UNDER = T5.TEAM_ID
                            LEFT JOIN
                                MS_TEAM T3
                            ON
                                T3.UNDER = T4.TEAM_ID
                            LEFT JOIN
                                MS_TEAM T2
                            ON
                                T2.UNDER = T3.TEAM_ID
                            LEFT JOIN
                                MS_TEAM T1
                            ON
                                T1.UNDER = T2.TEAM_ID ) T
                    ON
                        (
                            A.TEAM_ID = T.T5
                        OR  A.TEAM_ID = T4
                        OR  A.TEAM_ID = T3
                        OR  A.TEAM_ID = T2
                        OR  A.TEAM_ID = T1)
                    WHERE
                        T.USER_ID = CUR.USER_ID)) ;
*/
      SELECT SUM(APP_PER_DAY_ACTUAL)
      INTO V_APP_PER_DAY_ACTUAL
      FROM DHB_TEAM
      --WHERE SCHEDULE_ID = V_SCHEDULE_ID
      WHERE DHB_TYPE = V_DHB_TYPE
      AND DHP_SEQ = V_DHB_SEQ
      AND EXISTS (SELECT 1 FROM  MS_USER_TEAM MU
                  JOIN
                      ( SELECT  T5.TEAM_ID T5 ,
                              MT5.POSITION_ID,
                              MT5.USER_ID ,
                              T4.TEAM_ID T4,
                              T3.TEAM_ID T3 ,
                              T2.TEAM_ID T2,
                              T1.TEAM_ID T1
                          FROM  MS_TEAM T5
                          JOIN MS_USER_TEAM MT5 ON  T5.TEAM_ID = MT5.TEAM_ID
                          LEFT JOIN  MS_TEAM T4 ON  T4.TEAM_ID = T5.UNDER
                          LEFT JOIN  MS_TEAM T3 ON T3.TEAM_ID = T4.UNDER
                          LEFT JOIN MS_TEAM T2  ON T2.TEAM_ID = T3.UNDER
                          LEFT JOIN   MS_TEAM T1 ON T1.TEAM_ID = T2.UNDER ) T
                  ON
                      ( MU.TEAM_ID = T.T5
                      OR  MU.TEAM_ID = T4
                      OR  MU.TEAM_ID = T3
                      OR  MU.TEAM_ID = T2
                      OR  MU.TEAM_ID = T1)
                 WHERE MU.USER_ID =  CUR.USER_ID
                 --AND MU.TEAM_ID = DHB_TEAM.TEAM_ID);
                 AND T.T5 = DHB_TEAM.TEAM_ID);

/*
    SELECT COUNT ( DISTINCT B.BPD_INSTANCE_ID)
    INTO V_APP_PER_DAY_ACTUAL
    FROM LSW_TASK B
    JOIN LSW_USR_XREF U ON U.USER_ID = B.USER_ID
    WHERE B.STATUS = '32'
    AND TRUNC(CLOSE_DATETIME) =  TRUNC(V_APPLICATION_DATE)
    AND EXISTS
        (
            SELECT
                1
            FROM
                MS_USER_TEAM MU
            WHERE
                MU.USER_ID = U.USER_NAME
            AND MU.TEAM_ID IN
                (
                    SELECT
                        A.TEAM_ID
                    FROM
                        MS_USER_TEAM A
                    JOIN
                        (
                            SELECT
                                T5.TEAM_ID T5 ,
                                MT5.POSITION_ID,
                                MT5.USER_ID ,
                                T4.TEAM_ID T4,
                                T3.TEAM_ID T3 ,
                                T2.TEAM_ID T2,
                                T1.TEAM_ID T1
                            FROM
                                MS_TEAM T5
                            JOIN
                                MS_USER_TEAM MT5
                            ON
                                T5.TEAM_ID = MT5.TEAM_ID
                            LEFT JOIN
                                MS_TEAM T4
                            ON
                                T4.UNDER = T5.TEAM_ID
                            LEFT JOIN
                                MS_TEAM T3
                            ON
                                T3.UNDER = T4.TEAM_ID
                            LEFT JOIN
                                MS_TEAM T2
                            ON
                                T2.UNDER = T3.TEAM_ID
                            LEFT JOIN
                                MS_TEAM T1
                            ON
                                T1.UNDER = T2.TEAM_ID ) T
                    ON
                        (
                            A.TEAM_ID = T.T5
                        OR  A.TEAM_ID = T4
                        OR  A.TEAM_ID = T3
                        OR  A.TEAM_ID = T2
                        OR  A.TEAM_ID = T1)
                    WHERE
                        T.USER_ID =CUR.USER_ID
                    AND A.USER_ID = U.USER_NAME)) ;
    */
    SELECT VALUE1
    INTO V_KBANK_ROLE
    FROM WORKFLOW_PARAM
    WHERE PARAM_CODE = 'STAFF_KBANK_ROLE';

    SELECT
        24*(
        (
            SELECT  TO_DATE(VALUE1,'HH24:MI')
            FROM WORKFLOW_PARAM
            WHERE PARAM_CODE = 'WORKING_HR_END_KBANK') -
        (
            SELECT TO_DATE(VALUE1,'HH24:MI')
            FROM  WORKFLOW_PARAM
            WHERE PARAM_CODE = 'WORKING_HR_START_KBANK'))
    INTO V_KBANK_HOURS
    FROM DUAL;

    SELECT
        24*(
        (
            SELECT TO_DATE(VALUE1,'HH24:MI')
            FROM WORKFLOW_PARAM
            WHERE PARAM_CODE = 'WORKING_HR_END_PHR') -
        (
            SELECT TO_DATE(VALUE1,'HH24:MI')
            FROM WORKFLOW_PARAM
            WHERE PARAM_CODE = 'WORKING_HR_START_PHR'))
    INTO  V_PHR_HOURS
    FROM DUAL;

    SELECT
        NVL(SUM( V_KBANK_HOURS * NVL(RP.VALUE1,0)),0)
    INTO V_SUM_HOURS
    FROM  ORIG_IAS.USER_ROLE UR
    JOIN ORIG_IAS.ROLE R ON R.ROLE_ID = UR.ROLE_ID
                          AND PKA_DASHBOARD.F_CHECK_ROLE(R.ROLE_NAME ,V_KBANK_ROLE) = 1
    JOIN  REPORT_PARAM RP ON  RP.PARAM_TYPE = 'BASELINE_APP'
                           AND PARAM_CODE = R.ROLE_NAME
    WHERE
        EXISTS
        (
            SELECT
                1
            FROM
                MS_USER_TEAM MU
            WHERE
                MU.USER_ID = UR.USER_NAME
            AND MU.TEAM_ID IN
                (
                    SELECT
                        A.TEAM_ID
                    FROM
                        MS_USER_TEAM A
                    JOIN
                        (
                            SELECT
                                T5.TEAM_ID T5 ,
                                MT5.POSITION_ID,
                                MT5.USER_ID ,
                                T4.TEAM_ID T4,
                                T3.TEAM_ID T3 ,
                                T2.TEAM_ID T2,
                                T1.TEAM_ID T1
                            FROM
                                MS_TEAM T5
                            JOIN
                                MS_USER_TEAM MT5
                            ON
                                T5.TEAM_ID = MT5.TEAM_ID
                            LEFT JOIN
                                MS_TEAM T4
                            ON
                                T4.UNDER = T5.TEAM_ID
                            LEFT JOIN
                                MS_TEAM T3
                            ON
                                T3.UNDER = T4.TEAM_ID
                            LEFT JOIN
                                MS_TEAM T2
                            ON
                                T2.UNDER = T3.TEAM_ID
                            LEFT JOIN
                                MS_TEAM T1
                            ON
                                T1.UNDER = T2.TEAM_ID ) T
                    ON
                        (
                            A.TEAM_ID = T.T5
                        OR  A.TEAM_ID = T4
                        OR  A.TEAM_ID = T3
                        OR  A.TEAM_ID = T2
                        OR  A.TEAM_ID = T1)
                    WHERE
                        T.USER_ID =CUR.USER_ID
                    AND A.USER_ID = UR.USER_NAME)) ;

    SELECT
        NVL(SUM( V_PHR_HOURS * NVL(RP.VALUE1,0)),0) + V_SUM_HOURS
    INTO
        V_SUM_HOURS
    FROM
        ORIG_IAS.USER_ROLE UR
    JOIN
        ORIG_IAS.ROLE R
    ON
        R.ROLE_ID = UR.ROLE_ID
    AND PKA_DASHBOARD.F_CHECK_ROLE(R.ROLE_NAME ,V_KBANK_ROLE) = 0
    JOIN
        REPORT_PARAM RP
    ON
        RP.PARAM_TYPE = 'BASELINE_APP'
    AND PARAM_CODE = R.ROLE_NAME
    WHERE
        EXISTS
        (
            SELECT
                1
            FROM
                MS_USER_TEAM MU
            WHERE
                MU.USER_ID = UR.USER_NAME
            AND MU.TEAM_ID IN
                (
                    SELECT
                        A.TEAM_ID
                    FROM
                        MS_USER_TEAM A
                    JOIN
                        (
                            SELECT
                                T5.TEAM_ID T5 ,
                                MT5.POSITION_ID,
                                MT5.USER_ID ,
                                T4.TEAM_ID T4,
                                T3.TEAM_ID T3 ,
                                T2.TEAM_ID T2,
                                T1.TEAM_ID T1
                            FROM
                                MS_TEAM T5
                            JOIN
                                MS_USER_TEAM MT5
                            ON
                                T5.TEAM_ID = MT5.TEAM_ID
                            LEFT JOIN
                                MS_TEAM T4
                            ON
                                T4.UNDER = T5.TEAM_ID
                            LEFT JOIN
                                MS_TEAM T3
                            ON
                                T3.UNDER = T4.TEAM_ID
                            LEFT JOIN
                                MS_TEAM T2
                            ON
                                T2.UNDER = T3.TEAM_ID
                            LEFT JOIN
                                MS_TEAM T1
                            ON
                                T1.UNDER = T2.TEAM_ID ) T
                    ON
                        (
                            A.TEAM_ID = T.T5
                        OR  A.TEAM_ID = T4
                        OR  A.TEAM_ID = T3
                        OR  A.TEAM_ID = T2
                        OR  A.TEAM_ID = T1)
                    WHERE
                        T.USER_ID =CUR.USER_ID
                    AND A.USER_ID = UR.USER_NAME)) ;

    IF V_HOLIDAY = 1 THEN
        V_POINT_PER_DAY_TARGET := 0;
    V_SUM_HOURS := 0;
    END IF;


    INSERT
    INTO
        DHB_SUMMARY_DATA
        (
            SUMMARY_ID,
            POSITION_LEVEL ,
            DHB_TYPE ,
            DHB_OWNER ,
            DHB_GROUP,
            DHP_SEQ,
            VALUE1 ,
            DESCRIPTION1,
            VALUE2,
            DESCRIPTION2 ,
            VALUE3,
            DESCRIPTION3 ,
            VALUE4 ,
            DESCRIPTION4,
            CREATE_DATE,
            STATUS
        )
        VALUES
        (
            V_SUMMARY_ID,
            V_POSITION_LEVEL,
            V_DHB_TYPE,
            V_DHB_OWNER,
            V_DHB_GROUP,
            V_DHB_SEQ,
            V_POINT_PER_DAY_ACTUAL,
            'Point per day actual',
            V_POINT_PER_DAY_TARGET,
            'Point per day target',
            V_APP_PER_DAY_ACTUAL,
            'App per day actual',
            V_SUM_HOURS,
            'App per day target',
            V_APPLICATION_DATE,
            'W'
        );
    COMMIT;

    --WEEK --
    SELECT DHB_SUMMARY_DATA_PK.NEXTVAL
    INTO V_SUMMARY_ID
    FROM DUAL ;

    V_DHB_TYPE := '01';
    V_POSITION_LEVEL := CUR.POSITION_ID;
    V_DHB_OWNER := CUR.USER_ID;
    V_DHB_GROUP := 1;
    V_DHB_SEQ :=2;
    --Team Performance --

      SELECT SUM(NVL(POINT_PER_WEEK_ACTUAL,0))
      INTO V_POINT_PER_WEEK_ACTUAL
      FROM DHB_TEAM_WEEK
      --WHERE SCHEDULE_ID = V_SCHEDULE_ID
      WHERE DHB_TYPE = V_DHB_TYPE
      AND DHP_SEQ = V_DHB_SEQ
      AND EXISTS (SELECT 1 FROM  MS_USER_TEAM MU
                  JOIN
                      ( SELECT  T5.TEAM_ID T5 ,
                              MT5.POSITION_ID,
                              MT5.USER_ID ,
                              T4.TEAM_ID T4,
                              T3.TEAM_ID T3 ,
                              T2.TEAM_ID T2,
                              T1.TEAM_ID T1
                          FROM  MS_TEAM T5
                          JOIN MS_USER_TEAM MT5 ON  T5.TEAM_ID = MT5.TEAM_ID
                          LEFT JOIN  MS_TEAM T4 ON  T4.TEAM_ID = T5.UNDER
                          LEFT JOIN  MS_TEAM T3 ON T3.TEAM_ID = T4.UNDER
                          LEFT JOIN MS_TEAM T2  ON T2.TEAM_ID = T3.UNDER
                          LEFT JOIN   MS_TEAM T1 ON T1.TEAM_ID = T2.UNDER ) T
                  ON
                      ( MU.TEAM_ID = T.T5
                      OR  MU.TEAM_ID = T4
                      OR  MU.TEAM_ID = T3
                      OR  MU.TEAM_ID = T2
                      OR  MU.TEAM_ID = T1)
                 WHERE MU.USER_ID =  CUR.USER_ID
                 --AND MU.TEAM_ID = DHB_TEAM_WEEK.TEAM_ID );
                 AND T.T5 = DHB_TEAM_WEEK.TEAM_ID);

/*
    SELECT NVL(SUM(U.TOTAL_POINT),0)
    INTO V_POINT_PER_DAY_ACTUAL
    FROM USER_PERFORMANCE U
    WHERE WORKING_DATE BETWEEN TRUNC(V_START_DAY) AND (TRUNC(V_END_DAY)+0.99999)
    AND EXISTS
        (
            SELECT
                1
            FROM
                MS_USER_TEAM MU
            WHERE
                MU.USER_ID = U.USER_NAME
            AND MU.TEAM_ID IN
                (
                    SELECT
                        A.TEAM_ID
                    FROM
                        MS_USER_TEAM A
                    JOIN
                        (
                            SELECT
                                T5.TEAM_ID T5 ,
                                MT5.POSITION_ID,
                                MT5.USER_ID ,
                                T4.TEAM_ID T4,
                                T3.TEAM_ID T3 ,
                                T2.TEAM_ID T2,
                                T1.TEAM_ID T1
                            FROM
                                MS_TEAM T5
                            JOIN
                                MS_USER_TEAM MT5
                            ON
                                T5.TEAM_ID = MT5.TEAM_ID
                            LEFT JOIN
                                MS_TEAM T4
                            ON
                                T4.UNDER = T5.TEAM_ID
                            LEFT JOIN
                                MS_TEAM T3
                            ON
                                T3.UNDER = T4.TEAM_ID
                            LEFT JOIN
                                MS_TEAM T2
                            ON
                                T2.UNDER = T3.TEAM_ID
                            LEFT JOIN
                                MS_TEAM T1
                            ON
                                T1.UNDER = T2.TEAM_ID ) T
                    ON
                        (
                            A.TEAM_ID = T.T5
                        OR  A.TEAM_ID = T4
                        OR  A.TEAM_ID = T3
                        OR  A.TEAM_ID = T2
                        OR  A.TEAM_ID = T1)
                    WHERE
                        T.USER_ID =CUR.USER_ID
                    AND A.USER_ID = U.USER_NAME)) ;
*/

      SELECT SUM(NVL(POINT_PER_WEEK_TARGET,0))
      INTO V_POINT_PER_WEEK_TARGET
      FROM DHB_TEAM_WEEK
      --WHERE SCHEDULE_ID = V_SCHEDULE_ID
      WHERE DHB_TYPE = V_DHB_TYPE
      AND DHP_SEQ = V_DHB_SEQ
      AND EXISTS (SELECT 1 FROM  MS_USER_TEAM MU
                  JOIN
                      ( SELECT  T5.TEAM_ID T5 ,
                              MT5.POSITION_ID,
                              MT5.USER_ID ,
                              T4.TEAM_ID T4,
                              T3.TEAM_ID T3 ,
                              T2.TEAM_ID T2,
                              T1.TEAM_ID T1
                          FROM  MS_TEAM T5
                          JOIN MS_USER_TEAM MT5 ON  T5.TEAM_ID = MT5.TEAM_ID
                          LEFT JOIN  MS_TEAM T4 ON  T4.TEAM_ID = T5.UNDER
                          LEFT JOIN  MS_TEAM T3 ON T3.TEAM_ID = T4.UNDER
                          LEFT JOIN MS_TEAM T2  ON T2.TEAM_ID = T3.UNDER
                          LEFT JOIN   MS_TEAM T1 ON T1.TEAM_ID = T2.UNDER ) T
                  ON
                      ( MU.TEAM_ID = T.T5
                      OR  MU.TEAM_ID = T4
                      OR  MU.TEAM_ID = T3
                      OR  MU.TEAM_ID = T2
                      OR  MU.TEAM_ID = T1)
                 WHERE MU.USER_ID =  CUR.USER_ID
                 --AND MU.TEAM_ID = DHB_TEAM_WEEK.TEAM_ID);
                 AND T.T5 = DHB_TEAM_WEEK.TEAM_ID);
/*
    SELECT NVL(SUM(U.WORK_TARGET),0) * V_WORKING_DAY
    INTO V_POINT_PER_DAY_TARGET
    FROM US_USER_DETAIL U
    WHERE
        EXISTS
        (
            SELECT
                1
            FROM
                MS_USER_TEAM MU
            WHERE
                MU.USER_ID = U.USER_NAME
            AND MU.TEAM_ID IN
                (
                    SELECT
                        A.TEAM_ID
                    FROM
                        MS_USER_TEAM A
                    JOIN
                        (
                            SELECT
                                T5.TEAM_ID T5 ,
                                MT5.POSITION_ID,
                                MT5.USER_ID ,
                                T4.TEAM_ID T4,
                                T3.TEAM_ID T3 ,
                                T2.TEAM_ID T2,
                                T1.TEAM_ID T1
                            FROM
                                MS_TEAM T5
                            JOIN
                                MS_USER_TEAM MT5
                            ON
                                T5.TEAM_ID = MT5.TEAM_ID
                            LEFT JOIN
                                MS_TEAM T4
                            ON
                                T4.UNDER = T5.TEAM_ID
                            LEFT JOIN
                                MS_TEAM T3
                            ON
                                T3.UNDER = T4.TEAM_ID
                            LEFT JOIN
                                MS_TEAM T2
                            ON
                                T2.UNDER = T3.TEAM_ID
                            LEFT JOIN
                                MS_TEAM T1
                            ON
                                T1.UNDER = T2.TEAM_ID ) T
                    ON
                        (
                            A.TEAM_ID = T.T5
                        OR  A.TEAM_ID = T4
                        OR  A.TEAM_ID = T3
                        OR  A.TEAM_ID = T2
                        OR  A.TEAM_ID = T1)
                    WHERE
                        T.USER_ID =CUR.USER_ID
                    AND A.USER_ID = U.USER_NAME)) ;
 */
      SELECT SUM(NVL(APP_PER_WEEK_ACTUAL,0))
      INTO V_APP_PER_WEEK_ACTUAL
      FROM DHB_TEAM_WEEK
      --WHERE SCHEDULE_ID = V_SCHEDULE_ID
      WHERE DHB_TYPE = V_DHB_TYPE
      AND DHP_SEQ = V_DHB_SEQ
      AND EXISTS (SELECT 1 FROM  MS_USER_TEAM MU
                  JOIN
                      ( SELECT  T5.TEAM_ID T5 ,
                              MT5.POSITION_ID,
                              MT5.USER_ID ,
                              T4.TEAM_ID T4,
                              T3.TEAM_ID T3 ,
                              T2.TEAM_ID T2,
                              T1.TEAM_ID T1
                          FROM  MS_TEAM T5
                          JOIN MS_USER_TEAM MT5 ON  T5.TEAM_ID = MT5.TEAM_ID
                          LEFT JOIN  MS_TEAM T4 ON  T4.TEAM_ID = T5.UNDER
                          LEFT JOIN  MS_TEAM T3 ON T3.TEAM_ID = T4.UNDER
                          LEFT JOIN MS_TEAM T2  ON T2.TEAM_ID = T3.UNDER
                          LEFT JOIN   MS_TEAM T1 ON T1.TEAM_ID = T2.UNDER ) T
                  ON
                      ( MU.TEAM_ID = T.T5
                      OR  MU.TEAM_ID = T4
                      OR  MU.TEAM_ID = T3
                      OR  MU.TEAM_ID = T2
                      OR  MU.TEAM_ID = T1)
                 WHERE MU.USER_ID =  CUR.USER_ID
                 --AND MU.TEAM_ID = DHB_TEAM_WEEK.TEAM_ID);
                 AND T.T5 = DHB_TEAM_WEEK.TEAM_ID);

    /*
    SELECT
        COUNT ( DISTINCT B.BPD_INSTANCE_ID)
    INTO
        V_APP_PER_DAY_ACTUAL
    FROM
        LSW_TASK B
    JOIN
        LSW_USR_XREF U
    ON
        U.USER_ID = B.USER_ID
    WHERE
        B.STATUS = '32'
    AND TRUNC(CLOSE_DATETIME) BETWEEN TRUNC(V_START_DAY) AND TRUNC(V_END_DAY)
    AND EXISTS
        (
            SELECT
                1
            FROM
                MS_USER_TEAM MU
            WHERE
                MU.USER_ID = U.USER_NAME
            AND MU.TEAM_ID IN
                (
                    SELECT
                        A.TEAM_ID
                    FROM
                        MS_USER_TEAM A
                    JOIN
                        (
                            SELECT
                                T5.TEAM_ID T5 ,
                                MT5.POSITION_ID,
                                MT5.USER_ID ,
                                T4.TEAM_ID T4,
                                T3.TEAM_ID T3 ,
                                T2.TEAM_ID T2,
                                T1.TEAM_ID T1
                            FROM
                                MS_TEAM T5
                            JOIN
                                MS_USER_TEAM MT5
                            ON
                                T5.TEAM_ID = MT5.TEAM_ID
                            LEFT JOIN
                                MS_TEAM T4
                            ON
                                T4.UNDER = T5.TEAM_ID
                            LEFT JOIN
                                MS_TEAM T3
                            ON
                                T3.UNDER = T4.TEAM_ID
                            LEFT JOIN
                                MS_TEAM T2
                            ON
                                T2.UNDER = T3.TEAM_ID
                            LEFT JOIN
                                MS_TEAM T1
                            ON
                                T1.UNDER = T2.TEAM_ID ) T
                    ON
                        (
                            A.TEAM_ID = T.T5
                        OR  A.TEAM_ID = T4
                        OR  A.TEAM_ID = T3
                        OR  A.TEAM_ID = T2
                        OR  A.TEAM_ID = T1)
                    WHERE
                        T.USER_ID =CUR.USER_ID
                    AND A.USER_ID = U.USER_NAME)) ;
    */


    SELECT VALUE1
    INTO V_KBANK_ROLE
    FROM WORKFLOW_PARAM
    WHERE PARAM_CODE = 'STAFF_KBANK_ROLE';

    SELECT
        24*(
        (
            SELECT TO_DATE(VALUE1,'HH24:MI')
            FROM WORKFLOW_PARAM
            WHERE PARAM_CODE = 'WORKING_HR_END_KBANK') -
        (
            SELECT TO_DATE(VALUE1,'HH24:MI')
            FROM WORKFLOW_PARAM
            WHERE PARAM_CODE = 'WORKING_HR_START_KBANK'))
    INTO V_KBANK_HOURS
    FROM DUAL;

    SELECT
        24*(
        (
            SELECT TO_DATE(VALUE1,'HH24:MI')
            FROM WORKFLOW_PARAM
            WHERE  PARAM_CODE = 'WORKING_HR_END_PHR') -
        (
            SELECT TO_DATE(VALUE1,'HH24:MI')
            FROM WORKFLOW_PARAM
            WHERE PARAM_CODE = 'WORKING_HR_START_PHR'))
    INTO V_PHR_HOURS
    FROM DUAL;

    SELECT NVL(SUM( NVL(V_KBANK_HOURS,0) * NVL(RP.VALUE1,0)* NVL(V_WORKING_DAY,0)),0)
    INTO V_SUM_HOURS
    FROM ORIG_IAS.USER_ROLE UR
    JOIN ORIG_IAS.ROLE R  ON  R.ROLE_ID = UR.ROLE_ID
    AND PKA_DASHBOARD.F_CHECK_ROLE(R.ROLE_NAME ,V_KBANK_ROLE) = 1
    JOIN REPORT_PARAM RP ON RP.PARAM_TYPE = 'BASELINE_APP'
    AND PARAM_CODE = R.ROLE_NAME
    WHERE
        EXISTS
        (  SELECT  1  FROM  MS_USER_TEAM MU
            WHERE MU.USER_ID = UR.USER_NAME
            AND MU.TEAM_ID IN
                (
                    SELECT  A.TEAM_ID
                    FROM  MS_USER_TEAM A
                    JOIN
                        (
                            SELECT
                                T5.TEAM_ID T5 ,
                                MT5.POSITION_ID,
                                MT5.USER_ID ,
                                T4.TEAM_ID T4,
                                T3.TEAM_ID T3 ,
                                T2.TEAM_ID T2,
                                T1.TEAM_ID T1
                            FROM
                                MS_TEAM T5
                            JOIN
                                MS_USER_TEAM MT5
                            ON
                                T5.TEAM_ID = MT5.TEAM_ID
                            LEFT JOIN
                                MS_TEAM T4
                            ON
                                T4.UNDER = T5.TEAM_ID
                            LEFT JOIN
                                MS_TEAM T3
                            ON
                                T3.UNDER = T4.TEAM_ID
                            LEFT JOIN
                                MS_TEAM T2
                            ON
                                T2.UNDER = T3.TEAM_ID
                            LEFT JOIN
                                MS_TEAM T1
                            ON
                                T1.UNDER = T2.TEAM_ID ) T
                    ON
                        (
                            A.TEAM_ID = T.T5
                        OR  A.TEAM_ID = T4
                        OR  A.TEAM_ID = T3
                        OR  A.TEAM_ID = T2
                        OR  A.TEAM_ID = T1)
                    WHERE
                        T.USER_ID =CUR.USER_ID
                    AND A.USER_ID = UR.USER_NAME)) ;

    SELECT
        NVL(SUM( NVL(V_PHR_HOURS,0) * NVL(RP.VALUE1,0)* NVL(V_WORKING_DAY,0)),0) + V_SUM_HOURS
    INTO
        V_SUM_HOURS
    FROM
        ORIG_IAS.USER_ROLE UR
    JOIN
        ORIG_IAS.ROLE R
    ON
        R.ROLE_ID = UR.ROLE_ID
    AND PKA_DASHBOARD.F_CHECK_ROLE(R.ROLE_NAME ,V_KBANK_ROLE) = 0
    JOIN
        REPORT_PARAM RP
    ON
        RP.PARAM_TYPE = 'BASELINE_APP'
    AND PARAM_CODE = R.ROLE_NAME
    WHERE
        EXISTS
        (
            SELECT
                1
            FROM
                MS_USER_TEAM MU
            WHERE
                MU.USER_ID = UR.USER_NAME
            AND MU.TEAM_ID IN
                (
                    SELECT
                        A.TEAM_ID
                    FROM
                        MS_USER_TEAM A
                    JOIN
                        (
                            SELECT
                                T5.TEAM_ID T5 ,
                                MT5.POSITION_ID,
                                MT5.USER_ID ,
                                T4.TEAM_ID T4,
                                T3.TEAM_ID T3 ,
                                T2.TEAM_ID T2,
                                T1.TEAM_ID T1
                            FROM
                                MS_TEAM T5
                            JOIN
                                MS_USER_TEAM MT5
                            ON
                                T5.TEAM_ID = MT5.TEAM_ID
                            LEFT JOIN
                                MS_TEAM T4
                            ON
                                T4.UNDER = T5.TEAM_ID
                            LEFT JOIN
                                MS_TEAM T3
                            ON
                                T3.UNDER = T4.TEAM_ID
                            LEFT JOIN
                                MS_TEAM T2
                            ON
                                T2.UNDER = T3.TEAM_ID
                            LEFT JOIN
                                MS_TEAM T1
                            ON
                                T1.UNDER = T2.TEAM_ID ) T
                    ON
                        (
                            A.TEAM_ID = T.T5
                        OR  A.TEAM_ID = T4
                        OR  A.TEAM_ID = T3
                        OR  A.TEAM_ID = T2
                        OR  A.TEAM_ID = T1)
                    WHERE
                        T.USER_ID =CUR.USER_ID
                    AND A.USER_ID = UR.USER_NAME)) ;

    INSERT
    INTO
        DHB_SUMMARY_DATA
        (
            SUMMARY_ID,
            POSITION_LEVEL ,
            DHB_TYPE ,
            DHB_OWNER ,
            DHB_GROUP,
            DHP_SEQ,
            VALUE1 ,
            DESCRIPTION1,
            VALUE2,
            DESCRIPTION2 ,
            VALUE3,
            DESCRIPTION3 ,
            VALUE4 ,
            DESCRIPTION4,
            CREATE_DATE,
            STATUS
        )
        VALUES
        (
            V_SUMMARY_ID,
            V_POSITION_LEVEL,
            V_DHB_TYPE,
            V_DHB_OWNER,
            V_DHB_GROUP,
            V_DHB_SEQ,
            V_POINT_PER_WEEK_ACTUAL,
            'Point per week actual',
            V_POINT_PER_WEEK_TARGET,
            'Point per week target',
            V_APP_PER_WEEK_ACTUAL ,
            'App per week actual',
            V_SUM_HOURS,
            'App per week target',
            V_APPLICATION_DATE,
            'W'
        );
    COMMIT;

    END LOOP;

END P_GEN_DASHBOARD_01;

PROCEDURE P_GEN_DASHBOARD_02
AS
    V_DHB_TYPE VARCHAR2(2) := '02';
    V_POSITION_LEVEL VARCHAR2(3) := 'ALL';
    V_DBH_OWNER VARCHAR2(30) := 'ALL';
    V_DBH_GROUP NUMBER;
    V_DBH_SEQ NUMBER;
    V_SCHEDULE_ID NUMBER;
    V_SUMMARY_ID NUMBER;
    V_KBANK_ROLE VARCHAR2(100);
    V_COUNT_TEAM NUMBER;
    V_CAPACITY NUMBER;
    V_BASELINE_APP NUMBER;
    V_TODAY_FORCAST_APP NUMBER;
    V_INCOMING_APP_URGENT NUMBER;
    V_INCOMING_APP_ALL NUMBER;
    V_IN_QUEUE_URGENT NUMBER;
    V_IN_QUEUE_ALL NUMBER;
    V_INPUT NUMBER;
    V_ON_HAND_URGENT NUMBER;
    V_ON_HAND_WIP NUMBER;
    V_OUTPUT NUMBER;
    V_APPLICATION_DATE DATE;
    V_PROCEED_RATE NUMBER;
    V_WIP NUMBER;
    V_INPUT_12 NUMBER;
    V_WIP_12 NUMBER;
    V_OUTPUT_12 NUMBER;
    V_OLD_USER_ID VARCHAR2(30);
    V_SUBORDINATE_ROLE VARCHAR(100);
    V_INCOMING_APP T_INCOMING_APP;
BEGIN

    -- ALL --
    /* no need to delete data in procedure
    DELETE
    FROM
        DHB_SUMMARY_DATA
    WHERE
        DHB_TYPE = '02'; */
    SELECT MAX(SCHEDULE_ID) INTO V_SCHEDULE_ID
    FROM DHB_SCHEDULE;

    SELECT TRUNC(APP_DATE)
    INTO V_APPLICATION_DATE
    FROM APPLICATION_DATE;

    SELECT VALUE1/100
    INTO V_PROCEED_RATE
    FROM REPORT_PARAM
    WHERE PARAM_TYPE = 'PERCENT_PROCEED_RATE';

    SELECT
        COUNT(DISTINCT CASE WHEN TRUNC(APPLY_DATE) = TRUNC(V_APPLICATION_DATE) THEN  AG.APPLICATION_GROUP_ID ELSE NULL END),
        COUNT(DISTINCT CASE WHEN T.PARAM_CODE IS NOT NULL THEN  AG.APPLICATION_GROUP_ID ELSE NULL END ),
        COUNT(DISTINCT CASE WHEN TRUNC(DE2_SUBMIT_DATE) = TRUNC(V_APPLICATION_DATE) THEN  AG.APPLICATION_GROUP_ID ELSE NULL END )
    INTO  V_INPUT_12, V_WIP_12,V_OUTPUT_12
    FROM  ORIG_APPLICATION_GROUP AG
    LEFT JOIN  (SELECT
                SUBSTR (CSV, INSTR (CSV, ',', 1, LEV) + 1, INSTR (CSV, ',', 1, LEV + 1) - INSTR
                (CSV, ',', 1, LEV) - 1) JOB_STATE,
                PARAM_CODE
                FROM
                (
                    SELECT ',' || PARAM_VALUE || ',' CSV,
                        PARAM_CODE
                    FROM GENERAL_PARAM
                    WHERE PARAM_CODE LIKE 'WIP_JOBSTATE_%'
                    AND PARAM_CODE NOT IN ('WIP_JOBSTATE_CANCEL',
                                           'WIP_JOBSTATE_END')),
                (
                    SELECT LEVEL LEV
                    FROM DUAL CONNECT BY LEVEL <= 100)
            WHERE
                LEV <= LENGTH (CSV) - LENGTH (REPLACE (CSV, ',')) - 1) T
    ON T.JOB_STATE = AG.JOB_STATE
    AND T.PARAM_CODE LIKE 'WIP_JOBSTATE_%'
    LEFT JOIN
        ( SELECT MAX(AL.CREATE_DATE) DE2_SUBMIT_DATE,
                APPLICATION_GROUP_ID
            FROM  ORIG_APPLICATION_LOG AL
            WHERE  AL.JOB_STATE IN ('NM9901',
                                 'NM9902',
                                 'NM9903')
            GROUP BY  AL.APPLICATION_GROUP_ID) AGL
    ON  AGL.APPLICATION_GROUP_ID = AG.APPLICATION_GROUP_ID;

    SELECT VALUE1
    INTO  V_TODAY_FORCAST_APP
    FROM  REPORT_PARAM
    WHERE PARAM_TYPE = 'FORECAST_APPIN'
    AND PARAM_CODE = 'FORECAST_APPIN';

    V_OLD_USER_ID := NULL;
    -- BY USER  MANAGER ROLE --

    --GET INCOMING APP
    V_INCOMING_APP := F_COUNT_INCOMING_APP;

    FOR CUR IN
    (
        SELECT  USER_ID, (POSITION_ID) POSITION_ID
        FROM   MS_USER_TEAM
        WHERE  POSITION_ID IN (1,2,3,4)
        GROUP BY USER_ID,  (POSITION_ID)
        ORDER BY USER_ID)
    LOOP
        --CAPACITY --
        V_DHB_TYPE := '02';
        V_POSITION_LEVEL := CUR.POSITION_ID;
        V_DBH_OWNER := CUR.USER_ID;

        IF CUR.POSITION_ID IN (3, 4) THEN

            SELECT DHB_SUMMARY_DATA_PK.NEXTVAL
            INTO V_SUMMARY_ID
            FROM DUAL ;

        V_DBH_GROUP := 1;
        V_DBH_SEQ :=1;

        IF NVL(V_OLD_USER_ID,0) <> CUR.USER_ID THEN

            SELECT SUM(BASE_LINE_ALL) , COUNT(*)
            INTO V_CAPACITY,  V_COUNT_TEAM
            FROM
                (
                    SELECT DISTINCT  U.USER_ID, (RP.VALUE1*8) BASE_LINE_ALL
                    FROM  MS_USER_TEAM U
                    JOIN  ORIG_IAS.USER_ROLE UR  ON  UR.USER_NAME = U.USER_ID
                    JOIN  ORIG_IAS.ROLE R   ON  R.ROLE_ID = UR.ROLE_ID
                    JOIN  REPORT_PARAM RP  ON   RP.PARAM_TYPE = 'BASELINE_APP'
                    AND RP.PARAM_CODE = R.ROLE_NAME
                    WHERE -- EXISTS(SELECT 1 FROM MS_USER_TEAM MM WHERE MM.TEAM_ID = U.TEAM_ID AND MM.USER_ID =
                        -- V_DBH_OWNER  ));
                        EXISTS
                        (   SELECT
                                1
                            FROM
                                (
                                    SELECT
                                        T5.TEAM_ID T5 ,
                                        MT5.POSITION_ID,
                                        MT5.USER_ID ,
                                        T4.TEAM_ID T4,
                                        T3.TEAM_ID T3 ,
                                        T2.TEAM_ID T2,
                                        T1.TEAM_ID T1
                                    FROM
                                        MS_TEAM T5
                                    JOIN
                                        MS_USER_TEAM MT5
                                    ON
                                        T5.TEAM_ID = MT5.TEAM_ID
                                    LEFT JOIN
                                        MS_TEAM T4
                                    ON
                                        T4.UNDER = T5.TEAM_ID
                                    LEFT JOIN
                                        MS_TEAM T3
                                    ON
                                        T3.UNDER = T4.TEAM_ID
                                    LEFT JOIN
                                        MS_TEAM T2
                                    ON
                                        T2.UNDER = T3.TEAM_ID
                                    LEFT JOIN
                                        MS_TEAM T1
                                    ON
                                        T1.UNDER = T2.TEAM_ID ) T
                            WHERE
                                (
                                    U.TEAM_ID = T.T5
                                OR  U.TEAM_ID = T4
                                OR  U.TEAM_ID = T3
                                OR  U.TEAM_ID = T2
                                OR  U.TEAM_ID = T1)
                            AND T.USER_ID = V_DBH_OWNER));

        END IF; --IF NVL(V_OLD_USER_ID,0) <> CUR.USER_ID THEN

        INSERT INTO  DHB_SUMMARY_DATA
            (
                SUMMARY_ID,
                POSITION_LEVEL ,
                DHB_TYPE ,
                DHB_OWNER ,
                DHB_GROUP,
                DHP_SEQ,
                VALUE1 ,
                DESCRIPTION1,
                VALUE2,
                DESCRIPTION2 ,
                VALUE3,
                DESCRIPTION3 ,
                VALUE4 ,
                DESCRIPTION4,
                CREATE_DATE,
                STATUS
            )
            VALUES
            (
                V_SUMMARY_ID,
                V_POSITION_LEVEL,
                V_DHB_TYPE,
                V_DBH_OWNER,
                V_DBH_GROUP,
                V_DBH_SEQ,
                NVL(V_CAPACITY,0),
                'Capacity',
                '-1',
                NULL,
                NULL,
                NULL,
                NULL,
                NULL,
                V_APPLICATION_DATE,
                'W'
            );
        COMMIT;

        --TODAY FORECAST APP IN--
        SELECT  DHB_SUMMARY_DATA_PK.NEXTVAL
        INTO V_SUMMARY_ID
        FROM DUAL ;
        V_DBH_GROUP := 1;
        V_DBH_SEQ :=2;
/*        SELECT
            VALUE1
        INTO
            V_TODAY_FORCAST_APP
        FROM
            REPORT_PARAM
        WHERE
            PARAM_TYPE = 'FORECAST_APPIN'
        AND PARAM_CODE = 'FORECAST_APPIN';*/

        INSERT INTO  DHB_SUMMARY_DATA
            (
                SUMMARY_ID,
                POSITION_LEVEL ,
                DHB_TYPE ,
                DHB_OWNER ,
                DHB_GROUP,
                DHP_SEQ,
                VALUE1 ,
                DESCRIPTION1,
                VALUE2,
                DESCRIPTION2 ,
                VALUE3,
                DESCRIPTION3 ,
                VALUE4 ,
                DESCRIPTION4,
                CREATE_DATE,
                STATUS
            )
            VALUES
            (
                V_SUMMARY_ID,
                V_POSITION_LEVEL,
                V_DHB_TYPE,
                V_DBH_OWNER,
                V_DBH_GROUP,
                V_DBH_SEQ,
                V_TODAY_FORCAST_APP,
                'Today forecast app in',
                '-1',
                NULL,
                NULL,
                NULL,
                NULL,
                NULL,
                V_APPLICATION_DATE,
                'W'
            );
        COMMIT;

        --INCOMING APP URGENT--
        SELECT DHB_SUMMARY_DATA_PK.NEXTVAL
        INTO  V_SUMMARY_ID
        FROM  DUAL ;
        V_DHB_TYPE :='02';
        V_DBH_GROUP := 1;
        V_DBH_SEQ :=3;

        IF NVL(V_OLD_USER_ID,0) <> CUR.USER_ID THEN

--            SELECT SUM(INCOMING_APP_ALL)
--                  ,SUM(INCOMING_APP_URGENT)
--            INTO
--                V_INCOMING_APP_ALL,
--                V_INCOMING_APP_URGENT
--            FROM DHB_TEAM
--            --WHERE SCHEDULE_ID = V_SCHEDULE_ID
--            WHERE DHB_TYPE = V_DHB_TYPE
--            AND DHP_SEQ = V_DBH_SEQ
--            AND EXISTS (SELECT 1 FROM  MS_USER_TEAM MU
--                        JOIN
--                            ( SELECT  T5.TEAM_ID T5 ,
--                                    MT5.POSITION_ID,
--                                    MT5.USER_ID ,
--                                    T4.TEAM_ID T4,
--                                    T3.TEAM_ID T3 ,
--                                    T2.TEAM_ID T2,
--                                    T1.TEAM_ID T1
--                                FROM  MS_TEAM T5
--                                JOIN MS_USER_TEAM MT5 ON  T5.TEAM_ID = MT5.TEAM_ID
--                                LEFT JOIN  MS_TEAM T4 ON  T4.TEAM_ID = T5.UNDER
--                                LEFT JOIN  MS_TEAM T3 ON T3.TEAM_ID = T4.UNDER
--                                LEFT JOIN MS_TEAM T2  ON T2.TEAM_ID = T3.UNDER
--                                LEFT JOIN   MS_TEAM T1 ON T1.TEAM_ID = T2.UNDER ) T
--                        ON
--                            ( MU.TEAM_ID = T.T5
--                            OR  MU.TEAM_ID = T4
--                            OR  MU.TEAM_ID = T3
--                            OR  MU.TEAM_ID = T2
--                            OR  MU.TEAM_ID = T1)
--                       WHERE MU.USER_ID = V_DBH_OWNER
--                       --AND MU.TEAM_ID = DHB_TEAM.TEAM_ID);
--                       AND T.T5 = DHB_TEAM.TEAM_ID);
            V_SUBORDINATE_ROLE := F_GET_SUBORDINATE_ROLE(V_DBH_OWNER);
            IF(V_SUBORDINATE_ROLE = 'DE1_1') THEN
                V_INCOMING_APP_ALL := V_INCOMING_APP.DE1_1_NORMAL + V_INCOMING_APP.DE1_1_URGENT;
                V_INCOMING_APP_URGENT := V_INCOMING_APP.DE1_1_URGENT;
            ELSIF(V_SUBORDINATE_ROLE = 'DE1_2') THEN
                V_INCOMING_APP_ALL := V_INCOMING_APP.DE1_2_NORMAL + V_INCOMING_APP.DE1_2_URGENT;
                V_INCOMING_APP_URGENT := V_INCOMING_APP.DE1_2_URGENT;
            ELSIF(V_SUBORDINATE_ROLE = 'DV') THEN
                V_INCOMING_APP_ALL := V_INCOMING_APP.DV_NORMAL + V_INCOMING_APP.DV_URGENT;
                V_INCOMING_APP_URGENT := V_INCOMING_APP.DV_URGENT;
            ELSIF(V_SUBORDINATE_ROLE = 'VT') THEN
                V_INCOMING_APP_ALL := V_INCOMING_APP.VT_NORMAL + V_INCOMING_APP.VT_URGENT;
                V_INCOMING_APP_URGENT := V_INCOMING_APP.VT_URGENT;
            ELSIF(V_SUBORDINATE_ROLE = 'CA') THEN
                V_INCOMING_APP_ALL := V_INCOMING_APP.CA_NORMAL + V_INCOMING_APP.CA_URGENT;
                V_INCOMING_APP_URGENT := V_INCOMING_APP.CA_URGENT;
            ELSIF(V_SUBORDINATE_ROLE = 'DE2') THEN
                V_INCOMING_APP_ALL := V_INCOMING_APP.DE2_NORMAL + V_INCOMING_APP.DE2_URGENT;
                V_INCOMING_APP_URGENT := V_INCOMING_APP.DE2_URGENT;
            ELSE
                V_INCOMING_APP_ALL := 0;
                V_INCOMING_APP_URGENT := 0;
            END IF;
        END IF; --IF NVL(V_OLD_USER_ID,0) <> CUR.USER_ID THEN

        INSERT INTO DHB_SUMMARY_DATA
            (   SUMMARY_ID,
                POSITION_LEVEL ,
                DHB_TYPE ,
                DHB_OWNER ,
                DHB_GROUP,
                DHP_SEQ,
                VALUE1 ,
                DESCRIPTION1,
                VALUE2,
                DESCRIPTION2 ,
                VALUE3,
                DESCRIPTION3 ,
                VALUE4 ,
                DESCRIPTION4,
                CREATE_DATE,
                STATUS
            )
            VALUES
            (    V_SUMMARY_ID,
                V_POSITION_LEVEL,
                V_DHB_TYPE,
                V_DBH_OWNER,
                V_DBH_GROUP,
                V_DBH_SEQ,
                V_INCOMING_APP_URGENT*V_PROCEED_RATE,
                'Incoming App (Urgent/All)',
                V_INCOMING_APP_ALL*V_PROCEED_RATE,
                'Incoming app all',
                NULL,
                NULL,
                NULL,
                NULL,
                V_APPLICATION_DATE,
                'W'
            );
        COMMIT;

        --IN QUEUE URGENT--
        SELECT
            DHB_SUMMARY_DATA_PK.NEXTVAL
        INTO
            V_SUMMARY_ID
        FROM
            DUAL ;
        V_DBH_GROUP := 1;
        V_DBH_SEQ :=4;

        --In queue urgent--
        IF NVL(V_OLD_USER_ID,0) <> CUR.USER_ID THEN

            SELECT SUM(IN_QUEUE_ALL)
                  ,SUM(IN_QUEUE_URGENT)
            INTO
                V_IN_QUEUE_ALL,
                V_IN_QUEUE_URGENT
            FROM DHB_TEAM
            --WHERE SCHEDULE_ID = V_SCHEDULE_ID
            WHERE DHB_TYPE = V_DHB_TYPE
            AND DHP_SEQ = V_DBH_SEQ
            AND EXISTS (SELECT 1 FROM  MS_USER_TEAM MU
                        JOIN
                            ( SELECT  T5.TEAM_ID T5 ,
                                    MT5.POSITION_ID,
                                    MT5.USER_ID ,
                                    T4.TEAM_ID T4,
                                    T3.TEAM_ID T3 ,
                                    T2.TEAM_ID T2,
                                    T1.TEAM_ID T1
                                FROM  MS_TEAM T5
                                JOIN MS_USER_TEAM MT5 ON  T5.TEAM_ID = MT5.TEAM_ID
                                LEFT JOIN  MS_TEAM T4 ON  T4.TEAM_ID = T5.UNDER
                                LEFT JOIN  MS_TEAM T3 ON T3.TEAM_ID = T4.UNDER
                                LEFT JOIN MS_TEAM T2  ON T2.TEAM_ID = T3.UNDER
                                LEFT JOIN   MS_TEAM T1 ON T1.TEAM_ID = T2.UNDER ) T
                        ON
                            ( MU.TEAM_ID = T.T5
                            OR  MU.TEAM_ID = T4
                            OR  MU.TEAM_ID = T3
                            OR  MU.TEAM_ID = T2
                            OR  MU.TEAM_ID = T1)
                       WHERE MU.USER_ID = V_DBH_OWNER
                       --AND MU.TEAM_ID = DHB_TEAM.TEAM_ID);
                        AND T.T5 = DHB_TEAM.TEAM_ID);
        END IF; --IF NVL(V_OLD_USER_ID,0) <> CUR.USER_ID THEN

        INSERT
        INTO
            DHB_SUMMARY_DATA
            (
                SUMMARY_ID,
                POSITION_LEVEL ,
                DHB_TYPE ,
                DHB_OWNER ,
                DHB_GROUP,
                DHP_SEQ,
                VALUE1 ,
                DESCRIPTION1,
                VALUE2,
                DESCRIPTION2 ,
                VALUE3,
                DESCRIPTION3 ,
                VALUE4 ,
                DESCRIPTION4,
                CREATE_DATE,
                STATUS
            )
            VALUES
            (
                V_SUMMARY_ID,
                V_POSITION_LEVEL,
                V_DHB_TYPE,
                V_DBH_OWNER,
                V_DBH_GROUP,
                V_DBH_SEQ,
                V_IN_QUEUE_URGENT,
                'In Queue (Urgent/WIP1)',
                V_IN_QUEUE_ALL,
                'In queue all',
                NULL,
                NULL,
                NULL,
                NULL,
                V_APPLICATION_DATE,
                'W'
            );
        COMMIT;

        END IF; --IF CUR.POSITION_ID IN (3,4) THEN

        --INPUT--
        SELECT DHB_SUMMARY_DATA_PK.NEXTVAL
        INTO V_SUMMARY_ID
        FROM  DUAL ;
        V_DBH_GROUP := 1;
        V_DBH_SEQ :=5;

        IF NVL(V_OLD_USER_ID,0) <> CUR.USER_ID THEN

            SELECT SUM(INPUT),
                   SUM(ON_HAND_WIP ),
                   SUM(ON_HAND_URGENT ),
                   SUM(OUTPUT )
            INTO
                V_INPUT,
                V_ON_HAND_WIP,
                V_ON_HAND_URGENT,
                V_OUTPUT
            FROM DHB_TEAM
            --WHERE SCHEDULE_ID = V_SCHEDULE_ID
            WHERE DHB_TYPE = V_DHB_TYPE
            AND DHP_SEQ IN (5,6,7,8) --V_DBH_SEQ
            AND EXISTS (SELECT 1 FROM  MS_USER_TEAM MU
                        JOIN
                            ( SELECT  T5.TEAM_ID T5 ,
                                    MT5.POSITION_ID,
                                    MT5.USER_ID ,
                                    T4.TEAM_ID T4,
                                    T3.TEAM_ID T3 ,
                                    T2.TEAM_ID T2,
                                    T1.TEAM_ID T1
                                FROM  MS_TEAM T5
                                JOIN MS_USER_TEAM MT5 ON  T5.TEAM_ID = MT5.TEAM_ID
                                LEFT JOIN  MS_TEAM T4 ON  T4.TEAM_ID = T5.UNDER
                                LEFT JOIN  MS_TEAM T3 ON T3.TEAM_ID = T4.UNDER
                                LEFT JOIN MS_TEAM T2  ON T2.TEAM_ID = T3.UNDER
                                LEFT JOIN   MS_TEAM T1 ON T1.TEAM_ID = T2.UNDER ) T
                        ON
                            ( MU.TEAM_ID = T.T5
                            OR  MU.TEAM_ID = T4
                            OR  MU.TEAM_ID = T3
                            OR  MU.TEAM_ID = T2
                            OR  MU.TEAM_ID = T1)
                       WHERE MU.USER_ID = V_DBH_OWNER
                       --AND MU.TEAM_ID = DHB_TEAM.TEAM_ID);
                       AND T.T5 = DHB_TEAM.TEAM_ID);

        END IF;-- IF NVL(V_OLD_USER_ID,0) <> CUR.USER_ID THEN

        IF CUR.POSITION_ID IN (3, 4) THEN

            INSERT INTO DHB_SUMMARY_DATA
                (
                    SUMMARY_ID,
                    POSITION_LEVEL ,
                    DHB_TYPE ,
                    DHB_OWNER ,
                    DHB_GROUP,
                    DHP_SEQ,
                    VALUE1 ,
                    DESCRIPTION1,
                    VALUE2,
                    DESCRIPTION2 ,
                    VALUE3,
                    DESCRIPTION3 ,
                    VALUE4 ,
                    DESCRIPTION4,
                    CREATE_DATE,
                    STATUS
                )
                VALUES
                (
                    V_SUMMARY_ID,
                    V_POSITION_LEVEL,
                    V_DHB_TYPE,
                    V_DBH_OWNER,
                    V_DBH_GROUP,
                    V_DBH_SEQ,
                    V_INPUT,
                    'Input',
                    '-1',
                    NULL,
                    NULL,
                    NULL,
                    NULL,
                    NULL,
                    V_APPLICATION_DATE,
                    'W'
                );
            COMMIT;

        ELSE

            INSERT INTO DHB_SUMMARY_DATA
                (
                    SUMMARY_ID,
                    POSITION_LEVEL ,
                    DHB_TYPE ,
                    DHB_OWNER ,
                    DHB_GROUP,
                    DHP_SEQ,
                    VALUE1 ,
                    DESCRIPTION1,
                    VALUE2,
                    DESCRIPTION2 ,
                    VALUE3,
                    DESCRIPTION3 ,
                    VALUE4 ,
                    DESCRIPTION4,
                    CREATE_DATE,
                    STATUS
                )
                VALUES
                (
                    V_SUMMARY_ID,
                    V_POSITION_LEVEL,
                    V_DHB_TYPE,
                    V_DBH_OWNER,
                    V_DBH_GROUP,
                    V_DBH_SEQ,
                    V_INPUT_12,
                    'App in',
                    '-1',
                    NULL,
                    NULL,
                    NULL,
                    NULL,
                    NULL,
                    V_APPLICATION_DATE,
                    'W'
                );
            COMMIT;
        END IF; --IF CUR.POSITION_ID IN (3, 4) THEN

        --ON HAND URGENT  HAND WIP--
        SELECT DHB_SUMMARY_DATA_PK.NEXTVAL
        INTO V_SUMMARY_ID
        FROM DUAL ;

        V_DBH_GROUP := 1;
        V_DBH_SEQ :=6;
        --ON HAND URGENT  HAND WIP--

        IF CUR.POSITION_ID IN (3,  4) THEN

            INSERT  INTO DHB_SUMMARY_DATA
                (
                    SUMMARY_ID,
                    POSITION_LEVEL ,
                    DHB_TYPE ,
                    DHB_OWNER ,
                    DHB_GROUP,
                    DHP_SEQ,
                    VALUE1 ,
                    DESCRIPTION1,
                    VALUE2,
                    DESCRIPTION2 ,
                    VALUE3,
                    DESCRIPTION3 ,
                    VALUE4 ,
                    DESCRIPTION4,
                    CREATE_DATE,
                    STATUS
                )
                VALUES
                (
                    V_SUMMARY_ID,
                    V_POSITION_LEVEL,
                    V_DHB_TYPE,
                    V_DBH_OWNER,
                    V_DBH_GROUP,
                    V_DBH_SEQ,
                    V_ON_HAND_URGENT,
                    'On hand (Urgent/WIP2)',
                    V_ON_HAND_WIP,
                    'On hand WIP',
                    NULL,
                    NULL,
                    NULL,
                    NULL,
                    V_APPLICATION_DATE,
                    'W'
                );
        ELSE

        INSERT  INTO DHB_SUMMARY_DATA
            (
                SUMMARY_ID,
                POSITION_LEVEL ,
                DHB_TYPE ,
                DHB_OWNER ,
                DHB_GROUP,
                DHP_SEQ,
                VALUE1 ,
                DESCRIPTION1,
                VALUE2,
                DESCRIPTION2 ,
                VALUE3,
                DESCRIPTION3 ,
                VALUE4 ,
                DESCRIPTION4,
                CREATE_DATE,
                STATUS
            )
            VALUES
            (
                V_SUMMARY_ID,
                V_POSITION_LEVEL,
                V_DHB_TYPE,
                V_DBH_OWNER,
                V_DBH_GROUP,
                V_DBH_SEQ,
                V_WIP_12,
                'WIP',
                -1,
                NULL,
                NULL,
                NULL,
                NULL,
                NULL,
                V_APPLICATION_DATE,
                'W'
            );

        END IF; --IF CUR.POSITION_ID IN (3,  4) THEN

        COMMIT;

        --OUTPUT--

        SELECT  DHB_SUMMARY_DATA_PK.NEXTVAL
        INTO  V_SUMMARY_ID
        FROM  DUAL ;
        V_DBH_GROUP := 1;
        V_DBH_SEQ :=7;

        IF CUR.POSITION_ID IN (3, 4) THEN

            INSERT INTO DHB_SUMMARY_DATA
                (
                    SUMMARY_ID,
                    POSITION_LEVEL ,
                    DHB_TYPE ,
                    DHB_OWNER ,
                    DHB_GROUP,
                    DHP_SEQ,
                    VALUE1 ,
                    DESCRIPTION1,
                    VALUE2,
                    DESCRIPTION2 ,
                    VALUE3,
                    DESCRIPTION3 ,
                    VALUE4 ,
                    DESCRIPTION4,
                    CREATE_DATE,
                    STATUS
                )
                VALUES
                (
                    V_SUMMARY_ID,
                    V_POSITION_LEVEL,
                    V_DHB_TYPE,
                    V_DBH_OWNER,
                    V_DBH_GROUP,
                    V_DBH_SEQ,
                    V_OUTPUT,
                    'Output',
                    '-1',
                    NULL,
                    NULL,
                    NULL,
                    NULL,
                    NULL,
                    V_APPLICATION_DATE,
                    'W'
                );
        ELSE

            INSERT INTO DHB_SUMMARY_DATA
                (
                    SUMMARY_ID,
                    POSITION_LEVEL ,
                    DHB_TYPE ,
                    DHB_OWNER ,
                    DHB_GROUP,
                    DHP_SEQ,
                    VALUE1 ,
                    DESCRIPTION1,
                    VALUE2,
                    DESCRIPTION2 ,
                    VALUE3,
                    DESCRIPTION3 ,
                    VALUE4 ,
                    DESCRIPTION4,
                    CREATE_DATE,
                    STATUS
                )
                VALUES
                (
                    V_SUMMARY_ID,
                    V_POSITION_LEVEL,
                    V_DHB_TYPE,
                    V_DBH_OWNER,
                    V_DBH_GROUP,
                    V_DBH_SEQ,
                    V_OUTPUT_12,
                    'Output',
                    '-1',
                    NULL,
                    NULL,
                    NULL,
                    NULL,
                    NULL,
                    V_APPLICATION_DATE,
                    'W'
                );
        END IF; -- IF CUR.POSITION_ID IN (3, 4) THEN
        COMMIT;

        V_OLD_USER_ID := CUR.USER_ID;
    END LOOP;


END P_GEN_DASHBOARD_02;

PROCEDURE P_GEN_DASHBOARD_03
AS
V_DHB_TYPE VARCHAR2(2);
V_POSITION_LEVEL VARCHAR2(3) := 'ALL';
V_DHB_OWNER VARCHAR2(30) := 'ALL';
V_DHB_GROUP NUMBER;
V_DHB_SEQ NUMBER;
V_SCHEDULE_ID NUMBER;
V_SUMMARY_ID NUMBER;
V_KBANK_ROLE VARCHAR2(100);
V_COUNT_TEAM NUMBER;
V_ACCUM_OUTPUT_OF_TEAM NUMBER;
V_CAPACITY_OF_TEAM NUMBER;
V_PC_TEAM_CAPACITY_UTILIZATION NUMBER;
V_APPLICATION_DATE DATE;
BEGIN
    -- ALL --
    /* no need to delete data in procedure
    DELETE
    FROM
        DHB_SUMMARY_DATA
    WHERE
        DHB_TYPE = '03';*/

    --%TEAM CAPACITY UTILIZATION --

    SELECT DHB_SUMMARY_DATA_PK.NEXTVAL
    INTO V_SUMMARY_ID
    FROM DUAL ;

    SELECT TRUNC(APP_DATE) + (SYSDATE - TRUNC(SYSDATE))
    INTO V_APPLICATION_DATE
    FROM APPLICATION_DATE;

    -- BY USER  MANAGER ROLE --
    FOR CUR IN ( SELECT DISTINCT
                    USER_ID,
                    (POSITION_ID)POSITION_ID
                FROM  MS_USER_TEAM
                WHERE POSITION_ID IN (1,2,3,4))
    LOOP
        --%TEAM CAPACITY UTILIZATION --
        SELECT DHB_SUMMARY_DATA_PK.NEXTVAL
        INTO V_SUMMARY_ID
        FROM  DUAL ;

        V_DHB_TYPE := '03';
        V_POSITION_LEVEL := CUR.POSITION_ID;
        V_DHB_OWNER := CUR.USER_ID;
        V_DHB_GROUP := 1;
        V_DHB_SEQ :=1;
        V_PC_TEAM_CAPACITY_UTILIZATION := 0;
        V_ACCUM_OUTPUT_OF_TEAM := 0;
        V_CAPACITY_OF_TEAM := 0;

      SELECT SUM(NVL(OUTPUT,0))
      INTO V_ACCUM_OUTPUT_OF_TEAM
      FROM DHB_TEAM_HOUR
      --WHERE SCHEDULE_ID = V_SCHEDULE_ID
      WHERE DHB_TYPE = V_DHB_TYPE
      AND DHP_SEQ = V_DHB_SEQ
      AND EXISTS (SELECT 1 FROM  MS_USER_TEAM MU
                  JOIN
                      ( SELECT  T5.TEAM_ID T5 ,
                              MT5.POSITION_ID,
                              MT5.USER_ID ,
                              T4.TEAM_ID T4,
                              T3.TEAM_ID T3 ,
                              T2.TEAM_ID T2,
                              T1.TEAM_ID T1
                          FROM  MS_TEAM T5
                          JOIN MS_USER_TEAM MT5 ON  T5.TEAM_ID = MT5.TEAM_ID
                          LEFT JOIN  MS_TEAM T4 ON  T4.TEAM_ID = T5.UNDER
                          LEFT JOIN  MS_TEAM T3 ON T3.TEAM_ID = T4.UNDER
                          LEFT JOIN MS_TEAM T2  ON T2.TEAM_ID = T3.UNDER
                          LEFT JOIN   MS_TEAM T1 ON T1.TEAM_ID = T2.UNDER ) T
                  ON
                      ( MU.TEAM_ID = T.T5
                      OR  MU.TEAM_ID = T4
                      OR  MU.TEAM_ID = T3
                      OR  MU.TEAM_ID = T2
                      OR  MU.TEAM_ID = T1)
                 WHERE MU.USER_ID =  CUR.USER_ID
                 AND MU.TEAM_ID = DHB_TEAM_HOUR.TEAM_ID);

        /*
        SELECT COUNT(DISTINCT LT.TASK_ID )
        INTO V_ACCUM_OUTPUT_OF_TEAM
        FROM  LSW_TASK LT
        JOIN LSW_USR_XREF LU  ON LU.USER_ID = LT.USER_ID
        WHERE  LT.STATUS = '32'
        AND CLOSE_DATETIME BETWEEN TRUNC(V_APPLICATION_DATE,'HH24') AND TRUNC(V_APPLICATION_DATE + 1/24,  'HH24')
        AND EXISTS
            (
                SELECT
                    1
                FROM
                    MS_USER_TEAM U
                JOIN
                    (
                        SELECT
                            T5.TEAM_ID T5 ,
                            MT5.POSITION_ID,
                            MT5.USER_ID ,
                            T4.TEAM_ID T4,
                            T3.TEAM_ID T3 ,
                            T2.TEAM_ID T2,
                            T1.TEAM_ID T1
                        FROM
                            MS_TEAM T5
                        JOIN
                            MS_USER_TEAM MT5
                        ON
                            T5.TEAM_ID = MT5.TEAM_ID
                        LEFT JOIN
                            MS_TEAM T4
                        ON
                            T4.UNDER = T5.TEAM_ID
                        LEFT JOIN
                            MS_TEAM T3
                        ON
                            T3.UNDER = T4.TEAM_ID
                        LEFT JOIN
                            MS_TEAM T2
                        ON
                            T2.UNDER = T3.TEAM_ID
                        LEFT JOIN
                            MS_TEAM T1
                        ON
                            T1.UNDER = T2.TEAM_ID ) T
                ON
                    (
                        U.TEAM_ID = T.T5
                    OR  U.TEAM_ID = T4
                    OR  U.TEAM_ID = T3
                    OR  U.TEAM_ID = T2
                    OR  U.TEAM_ID = T1)
                WHERE
                    T.USER_ID = V_DHB_OWNER
                AND U.USER_ID = LU.USER_NAME ) ;
        */

      SELECT SUM(NVL(CAPACITY_OF_TEAM,0))
      INTO V_CAPACITY_OF_TEAM
      FROM DHB_TEAM_HOUR
      --WHERE SCHEDULE_ID = V_SCHEDULE_ID
      WHERE DHB_TYPE = V_DHB_TYPE
      AND DHP_SEQ = V_DHB_SEQ
      AND EXISTS (SELECT 1 FROM  MS_USER_TEAM MU
                  JOIN
                      ( SELECT  T5.TEAM_ID T5 ,
                              MT5.POSITION_ID,
                              MT5.USER_ID ,
                              T4.TEAM_ID T4,
                              T3.TEAM_ID T3 ,
                              T2.TEAM_ID T2,
                              T1.TEAM_ID T1
                          FROM  MS_TEAM T5
                          JOIN MS_USER_TEAM MT5 ON  T5.TEAM_ID = MT5.TEAM_ID
                          LEFT JOIN  MS_TEAM T4 ON  T4.TEAM_ID = T5.UNDER
                          LEFT JOIN  MS_TEAM T3 ON T3.TEAM_ID = T4.UNDER
                          LEFT JOIN MS_TEAM T2  ON T2.TEAM_ID = T3.UNDER
                          LEFT JOIN   MS_TEAM T1 ON T1.TEAM_ID = T2.UNDER ) T
                  ON
                      ( MU.TEAM_ID = T.T5
                      OR  MU.TEAM_ID = T4
                      OR  MU.TEAM_ID = T3
                      OR  MU.TEAM_ID = T2
                      OR  MU.TEAM_ID = T1)
                 WHERE MU.USER_ID =  CUR.USER_ID
                 AND MU.TEAM_ID = DHB_TEAM_HOUR.TEAM_ID);

        /*
        SELECT
            SUM(BASE_LINE_ALL) ,
            COUNT(*)
        INTO
            V_CAPACITY_OF_TEAM,
            V_COUNT_TEAM
        FROM
            (
                SELECT DISTINCT
                    U.USER_ID,
                    (RP.VALUE1 * ( TO_DATE(
                        CASE
                            WHEN TO_CHAR(TRUNC(SYSDATE+ 1/24,'HH24'),'HH24:MI') > END_TIME.VALUE1
                            THEN END_TIME.VALUE1
                            ELSE TO_CHAR(TRUNC(SYSDATE+ 1/24,'HH24'),'HH24:MI')
                        END ,'HH24:MI') - TO_DATE(
                        CASE
                            WHEN TO_CHAR(TRUNC(SYSDATE,'HH24'),'HH24:MI') < LPAD(START_TIME.VALUE1,5,'0')
                            THEN LPAD(START_TIME.VALUE1,5,'0')
                            ELSE TO_CHAR(TRUNC(SYSDATE,'HH24'),'HH24:MI')
                        END,'HH24:MI')) *24/1)    BASE_LINE_ALL ,
                    END_TIME.VALUE1               END_TIME,
                    LPAD(START_TIME.VALUE1,5,'0') START_TIME ,
                    ( TO_DATE(
                        CASE
                            WHEN TO_CHAR(TRUNC(SYSDATE+ 1/24,'HH24'),'HH24:MI') > END_TIME.VALUE1
                            THEN END_TIME.VALUE1
                            ELSE TO_CHAR(TRUNC(SYSDATE+ 1/24,'HH24'),'HH24:MI')
                        END ,'HH24:MI') - TO_DATE(
                        CASE
                            WHEN TO_CHAR(TRUNC(SYSDATE,'HH24'),'HH24:MI') < LPAD(START_TIME.VALUE1,5,'0')
                            THEN LPAD(START_TIME.VALUE1,5,'0')
                            ELSE TO_CHAR(TRUNC(SYSDATE,'HH24'),'HH24:MI')
                        END,'HH24:MI')) *24/1DURATION
                FROM
                    MS_USER_TEAM U
                JOIN
                    ORIG_IAS.USER_ROLE UR
                ON
                    UR.USER_NAME = U.USER_ID
                JOIN
                    ORIG_IAS.ROLE R
                ON
                    R.ROLE_ID = UR.ROLE_ID
                JOIN
                    REPORT_PARAM RP
                ON
                    RP.PARAM_TYPE = 'BASELINE_APP'
                AND RP.PARAM_CODE = R.ROLE_NAME
                LEFT JOIN
                    (
                        SELECT
                            SUBSTR (CSV, INSTR (CSV, ',', 1, LEV) + 1, INSTR (CSV, ',', 1, LEV + 1) - INSTR
                            (CSV, ',', 1, LEV) - 1) ROLE_NAME
                        FROM
                            (
                                SELECT
                                    ',' || VALUE1 || ',' CSV
                                FROM
                                    REPORT_PARAM
                                WHERE
                                    PARAM_TYPE = 'STAFF_KBANK_ROLE' ),
                            (
                                SELECT
                                    LEVEL LEV
                                FROM
                                    DUAL CONNECT BY LEVEL <= 100)
                        WHERE
                            LEV <= LENGTH (CSV) - LENGTH (REPLACE (CSV, ',')) - 1) KBANK
                ON
                    KBANK.ROLE_NAME = R.ROLE_NAME
                JOIN
                    WORKFLOW_PARAM END_TIME
                ON
                    END_TIME.PARAM_CODE = 'WORKING_HR_END_' || DECODE(KBANK.ROLE_NAME,NULL,'PHR','KBANK')
                JOIN
                    WORKFLOW_PARAM START_TIME
                ON
                    START_TIME.PARAM_CODE = 'WORKING_HR_START_' || DECODE(KBANK.ROLE_NAME,NULL,'PHR',
                    'KBANK')
                WHERE
                    EXISTS
                    (
                        SELECT
                            1
                        FROM
                            (
                                SELECT
                                    T5.TEAM_ID T5 ,
                                    MT5.POSITION_ID,
                                    MT5.USER_ID ,
                                    T4.TEAM_ID T4,
                                    T3.TEAM_ID T3 ,
                                    T2.TEAM_ID T2,
                                    T1.TEAM_ID T1
                                FROM
                                    MS_TEAM T5
                                JOIN
                                    MS_USER_TEAM MT5
                                ON
                                    T5.TEAM_ID = MT5.TEAM_ID
                                LEFT JOIN
                                    MS_TEAM T4
                                ON
                                    T4.UNDER = T5.TEAM_ID
                                LEFT JOIN
                                    MS_TEAM T3
                                ON
                                    T3.UNDER = T4.TEAM_ID
                                LEFT JOIN
                                    MS_TEAM T2
                                ON
                                    T2.UNDER = T3.TEAM_ID
                                LEFT JOIN
                                    MS_TEAM T1
                                ON
                                    T1.UNDER = T2.TEAM_ID ) T
                        WHERE
                            (
                                U.TEAM_ID = T.T5
                            OR  U.TEAM_ID = T4
                            OR  U.TEAM_ID = T3
                            OR  U.TEAM_ID = T2
                            OR  U.TEAM_ID = T1)
                        AND T.USER_ID = V_DHB_OWNER));
        */

        IF NVL(V_CAPACITY_OF_TEAM,0) = 0 THEN
            V_PC_TEAM_CAPACITY_UTILIZATION := 0;
        ELSE
            V_PC_TEAM_CAPACITY_UTILIZATION := V_ACCUM_OUTPUT_OF_TEAM/V_CAPACITY_OF_TEAM*100;
        END IF;

        INSERT
        INTO
            DHB_SUMMARY_DATA
            (
                SUMMARY_ID,
                POSITION_LEVEL ,
                DHB_TYPE ,
                DHB_OWNER ,
                DHB_GROUP,
                DHP_SEQ,
                VALUE1 ,
                DESCRIPTION1,
                VALUE2,
                DESCRIPTION2 ,
                VALUE3,
                DESCRIPTION3 ,
                VALUE4 ,
                DESCRIPTION4,
                CREATE_DATE,
                STATUS
            )
            VALUES
            (
                V_SUMMARY_ID,
                V_POSITION_LEVEL,
                V_DHB_TYPE,
                V_DHB_OWNER,
                V_DHB_GROUP,
                V_DHB_SEQ,
                ROUND(V_PC_TEAM_CAPACITY_UTILIZATION,2),
                '%Team Capacity Utilization',
                NULL,
                NULL,
                NULL,
                NULL,
                NULL,
                NULL,
                V_APPLICATION_DATE,
                'W'
            );
        COMMIT;
    END LOOP;
END P_GEN_DASHBOARD_03;

/*PROCEDURE P_GEN_DASHBOARD_04
AS
    V_DHB_TYPE VARCHAR2(2) := '01';
    V_POSITION_LEVEL VARCHAR2(3) := 'ALL';
    V_DBH_OWNER VARCHAR2(30) := 'ALL';
    V_DBH_GROUP NUMBER;
    V_DBH_SEQ NUMBER;
    V_SUMMARY_ID NUMBER;
    V_SCHEDULE_ID NUMBER;
    V_KBANK_ROLE VARCHAR2(100);
    V_COUNT_TEAM NUMBER;
    V_TOTAL_TEAM_OUTPUT_APP_MONTH NUMBER;
    V_TEAM_ERROR_CAPTURED NUMBER;
    V_TEAM_ERROR_MISS NUMBER;
    V_TEAM_QUALITY NUMBER;
    V_TEAM_QUALITY_MISS NUMBER;
    V_APPLICATION_DATE DATE;
    V_OLD_USER_ID VARCHAR2(30);
BEGIN
    -- ALL --
    /* no need to delete data in procedure
    DELETE
    FROM
        DHB_SUMMARY_DATA
    WHERE
        DHB_TYPE = '04';*/
    /*SELECT MAX(SCHEDULE_ID) INTO V_SCHEDULE_ID
    FROM DHB_SCHEDULE;

    SELECT TRUNC(APP_DATE) + (SYSDATE - TRUNC(SYSDATE))
    INTO  V_APPLICATION_DATE
    FROM  APPLICATION_DATE;
    -- BY USER  MANAGER ROLE --

    SELECT  VALUE1
    INTO V_TEAM_ERROR_CAPTURED
    FROM  REPORT_PARAM
    WHERE PARAM_CODE = 'ERROR_FROM_CUST';

    V_OLD_USER_ID := NULL;
    FOR CUR IN  (SELECT DISTINCT
                  USER_ID,
                  (POSITION_ID) POSITION_ID
                FROM MS_USER_TEAM
                WHERE  POSITION_ID IN (1,2,3,4)
                ORDER BY USER_ID)
    LOOP
        --%TEAM QUALITY --
        SELECT DHB_SUMMARY_DATA_PK.NEXTVAL
        INTO V_SUMMARY_ID
        FROM DUAL ;
        V_DHB_TYPE := '04';
        V_POSITION_LEVEL := CUR.POSITION_ID;
        V_DBH_OWNER := CUR.USER_ID;
        V_DBH_GROUP := 1;
        V_DBH_SEQ :=1;

        IF NVL(V_OLD_USER_ID,'X') <> CUR.USER_ID THEN

            SELECT SUM(NVL(OUTPUT,0))
                  ,SUM(NVL(TEAM_ERROR_CAPTURED,0))
            INTO
                V_TOTAL_TEAM_OUTPUT_APP_MONTH,
                V_TEAM_ERROR_CAPTURED
            FROM DHB_TEAM_MONTH
            WHERE SCHEDULE_ID = V_SCHEDULE_ID
            AND DHP_SEQ = V_DBH_SEQ
            AND EXISTS (SELECT 1 FROM  MS_USER_TEAM MU
                        JOIN
                            ( SELECT  T5.TEAM_ID T5 ,
                                    MT5.POSITION_ID,
                                    MT5.USER_ID ,
                                    T4.TEAM_ID T4,
                                    T3.TEAM_ID T3 ,
                                    T2.TEAM_ID T2,
                                    T1.TEAM_ID T1
                                FROM  MS_TEAM T5
                                JOIN MS_USER_TEAM MT5 ON  T5.TEAM_ID = MT5.TEAM_ID
                                LEFT JOIN  MS_TEAM T4 ON  T4.TEAM_ID = T5.UNDER
                                LEFT JOIN  MS_TEAM T3 ON T3.TEAM_ID = T4.UNDER
                                LEFT JOIN MS_TEAM T2  ON T2.TEAM_ID = T3.UNDER
                                LEFT JOIN   MS_TEAM T1 ON T1.TEAM_ID = T2.UNDER ) T
                        ON
                            ( MU.TEAM_ID = T.T5
                            OR  MU.TEAM_ID = T4
                            OR  MU.TEAM_ID = T3
                            OR  MU.TEAM_ID = T2
                            OR  MU.TEAM_ID = T1)
                       WHERE MU.USER_ID = V_DBH_OWNER
                       AND MU.TEAM_ID = DHB_TEAM_MONTH.TEAM_ID);

        /*
            SELECT
                COUNT(DISTINCT LT.BPD_INSTANCE_ID )
            INTO
                V_TOTAL_TEAM_OUTPUT_APP_MONTH
            FROM
                LSW_TASK LT
            JOIN
                LSW_USR_XREF LU
            ON
                LU.USER_ID = LT.USER_ID
            WHERE
                LT.STATUS = '32'
            AND TO_CHAR(CLOSE_DATETIME,'YYYYMM') = TO_CHAR(V_APPLICATION_DATE,'YYYYMM')
            AND EXISTS
                (
                    SELECT
                        *
                    FROM
                        MS_USER_TEAM U
                    JOIN
                        (
                            SELECT
                                T5.TEAM_ID T5 ,
                                MT5.POSITION_ID,
                                MT5.USER_ID ,
                                T4.TEAM_ID T4,
                                T3.TEAM_ID T3 ,
                                T2.TEAM_ID T2,
                                T1.TEAM_ID T1
                            FROM
                                MS_TEAM T5
                            JOIN
                                MS_USER_TEAM MT5
                            ON
                                T5.TEAM_ID = MT5.TEAM_ID
                            LEFT JOIN
                                MS_TEAM T4
                            ON
                                T4.TEAM_ID = T5.UNDER
                            LEFT JOIN
                                MS_TEAM T3
                            ON
                                T3.TEAM_ID = T4.UNDER
                            LEFT JOIN
                                MS_TEAM T2
                            ON
                                T2.TEAM_ID = T3.UNDER
                            LEFT JOIN
                                MS_TEAM T1
                            ON
                                T1.TEAM_ID = T2.UNDER ) T
                    ON
                        (
                            U.TEAM_ID = T.T5
                        OR  U.TEAM_ID = T4
                        OR  U.TEAM_ID = T3
                        OR  U.TEAM_ID = T2
                        OR  U.TEAM_ID = T1)
                    WHERE
                        U.USER_ID = CUR.USER_ID
                    AND T.USER_ID = LU.USER_NAME ) ;

            SELECT
                COUNT(DISTINCT LT.BPD_INSTANCE_ID )
            INTO
                V_TEAM_ERROR_CAPTURED
            FROM
                LSW_TASK LT
            JOIN
                LSW_USR_XREF LU
            ON
                LU.USER_ID = LT.USER_ID
            JOIN
                MS_USER_TEAM U
            ON
                U.USER_ID = LU.USER_NAME
            WHERE
                LT.STATUS = '32'
            AND TO_CHAR(CLOSE_DATETIME,'YYYYMM') = TO_CHAR(V_APPLICATION_DATE,'YYYYMM')
            AND EXISTS
                (
                    SELECT
                        *
                    FROM
                        MS_USER_TEAM U
                    JOIN
                        (
                            SELECT
                                T5.TEAM_ID T5 ,
                                MT5.POSITION_ID,
                                MT5.USER_ID ,
                                T4.TEAM_ID T4,
                                T3.TEAM_ID T3 ,
                                T2.TEAM_ID T2,
                                T1.TEAM_ID T1
                            FROM
                                MS_TEAM T5
                            JOIN
                                MS_USER_TEAM MT5
                            ON
                                T5.TEAM_ID = MT5.TEAM_ID
                            LEFT JOIN
                                MS_TEAM T4
                            ON
                                T4.TEAM_ID = T5.UNDER
                            LEFT JOIN
                                MS_TEAM T3
                            ON
                                T3.TEAM_ID = T4.UNDER
                            LEFT JOIN
                                MS_TEAM T2
                            ON
                                T2.TEAM_ID = T3.UNDER
                            LEFT JOIN
                                MS_TEAM T1
                            ON
                                T1.TEAM_ID = T2.UNDER ) T
                    ON
                        (
                            U.TEAM_ID = T.T5
                        OR  U.TEAM_ID = T4
                        OR  U.TEAM_ID = T3
                        OR  U.TEAM_ID = T2
                        OR  U.TEAM_ID = T1)
                    WHERE
                        U.USER_ID = CUR.USER_ID
                    AND T.USER_ID = LU.USER_NAME )
            AND (
                    EXISTS
                    (
                        SELECT
                            1
                        FROM
                            ORIG_APPLICATION_GROUP OG
                        JOIN
                            ORIG_COMPARISON_DATA OCD
                        ON
                            OCD.APPLICATION_GROUP_ID = OG.APPLICATION_GROUP_ID
                        WHERE
                            OG.INSTANT_ID = LT.BPD_INSTANCE_ID
                        AND OCD.COMPARE_FLAG = 'W'
                        AND OCD.SRC_OF_DATA = '2MAKER'
                        AND TO_CHAR(OCD.UPDATE_DATE,'YYYYMM') = TO_CHAR(V_APPLICATION_DATE,'YYYYMM')
                        AND EXISTS
                            (
                                SELECT
                                    *
                                FROM
                                    MS_USER_TEAM U
                                JOIN
                                    (
                                        SELECT
                                            T5.TEAM_ID T5 ,
                                            MT5.POSITION_ID,
                                            MT5.USER_ID ,
                                            T4.TEAM_ID T4,
                                            T3.TEAM_ID T3 ,
                                            T2.TEAM_ID T2,
                                            T1.TEAM_ID T1
                                        FROM
                                            MS_TEAM T5
                                        JOIN
                                            MS_USER_TEAM MT5
                                        ON
                                            T5.TEAM_ID = MT5.TEAM_ID
                                        LEFT JOIN
                                            MS_TEAM T4
                                        ON
                                            T4.TEAM_ID = T5.UNDER
                                        LEFT JOIN
                                            MS_TEAM T3
                                        ON
                                            T3.TEAM_ID = T4.UNDER
                                        LEFT JOIN
                                            MS_TEAM T2
                                        ON
                                            T2.TEAM_ID = T3.UNDER
                                        LEFT JOIN
                                            MS_TEAM T1
                                        ON
                                            T1.TEAM_ID = T2.UNDER ) T
                                ON
                                    (
                                        U.TEAM_ID = T.T5
                                    OR  U.TEAM_ID = T4
                                    OR  U.TEAM_ID = T3
                                    OR  U.TEAM_ID = T2
                                    OR  U.TEAM_ID = T1)
                                WHERE
                                    U.USER_ID = CUR.USER_ID
                                AND T.USER_ID = OCD.CREATE_BY ) )
                OR  EXISTS
                    (
                        SELECT
                            1
                        FROM
                            ORIG_APPLICATION_GROUP OG
                        JOIN
                            ORIG_PERSONAL_INFO OP
                        ON
                            OP.APPLICATION_GROUP_ID = OG.APPLICATION_GROUP_ID
                        JOIN
                            INC_INFO INC
                        ON
                            INC.PERSONAL_ID = OP.PERSONAL_ID
                        JOIN
                            (
                                SELECT
                                    T5.TEAM_ID T5 ,
                                    MT5.POSITION_ID,
                                    MT5.USER_ID ,
                                    T4.TEAM_ID T4,
                                    T3.TEAM_ID T3 ,
                                    T2.TEAM_ID T2,
                                    T1.TEAM_ID T1
                                FROM
                                    MS_TEAM T5
                                JOIN
                                    MS_USER_TEAM MT5
                                ON
                                    T5.TEAM_ID = MT5.TEAM_ID
                                LEFT JOIN
                                    MS_TEAM T4
                                ON
                                    T4.TEAM_ID = T5.UNDER
                                LEFT JOIN
                                    MS_TEAM T3
                                ON
                                    T3.TEAM_ID = T4.UNDER
                                LEFT JOIN
                                    MS_TEAM T2
                                ON
                                    T2.TEAM_ID = T3.UNDER
                                LEFT JOIN
                                    MS_TEAM T1
                                ON
                                    T1.TEAM_ID = T2.UNDER ) T
                        ON
                            (
                                U.TEAM_ID = T.T5
                            OR  U.TEAM_ID = T4
                            OR  U.TEAM_ID = T3
                            OR  U.TEAM_ID = T2
                            OR  U.TEAM_ID = T1)
                        WHERE
                            OG.INSTANT_ID = LT.BPD_INSTANCE_ID
                        AND INC.COMPARE_FLAG = 'W'
                        AND INC.CREATE_BY = T.USER_ID ) ) ;
            */
        /*END IF;

        IF V_TOTAL_TEAM_OUTPUT_APP_MONTH = 0 THEN
            V_TEAM_QUALITY := 0;
        ELSE
            V_TEAM_QUALITY := ( V_TOTAL_TEAM_OUTPUT_APP_MONTH - V_TEAM_ERROR_CAPTURED)*100 /
            V_TOTAL_TEAM_OUTPUT_APP_MONTH ;
        END IF;


        INSERT
        INTO
            DHB_SUMMARY_DATA
            (
                SUMMARY_ID,
                POSITION_LEVEL ,
                DHB_TYPE ,
                DHB_OWNER ,
                DHB_GROUP,
                DHP_SEQ,
                VALUE1 ,
                DESCRIPTION1,
                VALUE2,
                DESCRIPTION2 ,
                VALUE3,
                DESCRIPTION3 ,
                VALUE4 ,
                DESCRIPTION4,
                CREATE_DATE,
                STATUS
            )
            VALUES
            (
                V_SUMMARY_ID,
                V_POSITION_LEVEL,
                V_DHB_TYPE,
                V_DBH_OWNER,
                V_DBH_GROUP,
                V_DBH_SEQ,
                ROUND(V_TEAM_QUALITY,2),
                '%Near Miss',
                NULL,
                NULL,
                NULL,
                NULL,
                NULL,
                NULL,
                V_APPLICATION_DATE,
                'W'
            );
        COMMIT;


        --%Miss = [(Head of. output application of month ?error from customer of month(Key in value))x100]
        -- /total Head of. output application of month

/*        SELECT
            VALUE1
        INTO
            V_TEAM_ERROR_CAPTURED
        FROM
            REPORT_PARAM
        WHERE
            PARAM_CODE = 'ERROR_FROM_CUST';*/

        /*IF CUR.POSITION_ID IN (1,2) THEN

        IF V_TOTAL_TEAM_OUTPUT_APP_MONTH = 0 THEN
                V_TEAM_QUALITY := 0;
        ELSE
            V_TEAM_QUALITY := ( V_TOTAL_TEAM_OUTPUT_APP_MONTH - V_TEAM_ERROR_CAPTURED)*100 /
            V_TOTAL_TEAM_OUTPUT_APP_MONTH ;
        END IF;

        SELECT DHB_SUMMARY_DATA_PK.NEXTVAL
        INTO V_SUMMARY_ID
        FROM  DUAL ;
        V_DBH_GROUP := 1;
        V_DBH_SEQ :=2;

        INSERT INTO
            DHB_SUMMARY_DATA
            (
                SUMMARY_ID,
                POSITION_LEVEL ,
                DHB_TYPE ,
                DHB_OWNER ,
                DHB_GROUP,
                DHP_SEQ,
                VALUE1 ,
                DESCRIPTION1,
                VALUE2,
                DESCRIPTION2 ,
                VALUE3,
                DESCRIPTION3 ,
                VALUE4 ,
                DESCRIPTION4,
                CREATE_DATE,
                STATUS
            )
            VALUES
            (
                V_SUMMARY_ID,
                V_POSITION_LEVEL,
                V_DHB_TYPE,
                V_DBH_OWNER,
                V_DBH_GROUP,
                V_DBH_SEQ,
                ROUND(V_TEAM_QUALITY,2),
                '%Miss',
                NULL,
                NULL,
                NULL,
                NULL,
                NULL,
                NULL,
                V_APPLICATION_DATE,
                'W'
            );
        COMMIT;
        END IF;
        V_OLD_USER_ID := CUR.USER_ID;


    END LOOP;
END P_GEN_DASHBOARD_04;*/

PROCEDURE P_GEN_DASHBOARD_04
AS
    V_DHB_TYPE VARCHAR2(2) := '01';
    V_POSITION_LEVEL VARCHAR2(3) := 'ALL';
    V_DBH_OWNER VARCHAR2(30) := 'ALL';
    V_DBH_GROUP NUMBER;
    V_DBH_SEQ NUMBER;
    V_SUMMARY_ID NUMBER;
    V_KBANK_ROLE VARCHAR2(100);
    V_COUNT_TEAM NUMBER;
    V_TOTAL_TEAM_OUTPUT_APP_MONTH NUMBER;
    V_TEAM_ERROR_CAPTURED NUMBER;
    V_ERROR_FROM_CUST_PARAM NUMBER;
    V_TEAM_ERROR_MISS NUMBER;
    V_TEAM_QUALITY NUMBER;
    V_TEAM_QUALITY_MISS NUMBER;
    V_APPLICATION_DATE DATE;
    V_OLD_USER_ID VARCHAR2(30);
    V_OLD_TEAM_ID VARCHAR2(30);

BEGIN
    -- ALL --
    /* no need to delete data in procedure
    DELETE
    FROM
        DHB_SUMMARY_DATA
    WHERE
        DHB_TYPE = '04';*/

    SELECT
        TRUNC(APP_DATE) + (SYSDATE - TRUNC(SYSDATE))
    INTO
        V_APPLICATION_DATE
    FROM
        APPLICATION_DATE;
    -- BY USER  MANAGER ROLE --

    SELECT
        VALUE1
    INTO
        V_ERROR_FROM_CUST_PARAM
    FROM
        REPORT_PARAM
    WHERE
        PARAM_CODE = 'ERROR_FROM_CUST';

    V_OLD_USER_ID := NULL;
    FOR CUR IN
                (
                SELECT DISTINCT
                    USER_ID,
                    (POSITION_ID) POSITION_ID, TEAM_ID
                FROM
                    MS_USER_TEAM
                WHERE
                    POSITION_ID IN (1,2,
                                    3,4)
                GROUP BY USER_ID, POSITION_ID, TEAM_ID
                ORDER BY TEAM_ID,USER_ID
                )
    LOOP
        --%TEAM QUALITY --
        SELECT
            DHB_SUMMARY_DATA_PK.NEXTVAL
        INTO
            V_SUMMARY_ID
        FROM
            DUAL ;
        V_DHB_TYPE := '04';
        V_POSITION_LEVEL := CUR.POSITION_ID;
        V_DBH_OWNER := CUR.USER_ID;
        V_DBH_GROUP := 1;
        V_DBH_SEQ :=1;

        IF NVL(V_OLD_TEAM_ID,'X') <> CUR.TEAM_ID THEN
--            SELECT  COUNT(DISTINCT LT.BPD_INSTANCE_ID)
--              INTO
--                V_TOTAL_TEAM_OUTPUT_APP_MONTH
--            FROM
--           (SELECT USER_ID, POSITION_ID, MIN(PATH) PATH
--            FROM
--                (SELECT B.USER_ID, B.POSITION_ID, A.TEAM_ID, SYS_CONNECT_BY_PATH(A.TEAM_ID,'/') PATH
--                FROM MS_TEAM A
--                JOIN MS_USER_TEAM B ON A.TEAM_ID = B.TEAM_ID
--                CONNECT BY PRIOR A.TEAM_ID = A.UNDER)
--                GROUP BY USER_ID, POSITION_ID) T
--             JOIN
--                    LSW_USR_XREF LU
--                ON T.USER_ID = LU.USER_NAME
--             JOIN
--                    DHB_LSW_TASK LT
--                ON  LU.USER_ID = LT.USER_ID
--            WHERE
--                INSTR(T.PATH,CUR.TEAM_ID) > 0;
            SELECT  COUNT(DISTINCT LT.BPD_INSTANCE_ID)
            INTO
                V_TOTAL_TEAM_OUTPUT_APP_MONTH
            FROM
            LSW_USR_XREF LU
             JOIN
                    DHB_LSW_TASK LT
                ON  LU.USER_ID = LT.USER_ID
            WHERE
                LU.USER_NAME IN (
                    SELECT DISTINCT USER_ID
                FROM MS_USER_TEAM UT
                WHERE UT.TEAM_ID IN(
                    SELECT TEAM_ID
                    FROM MS_TEAM
                    START WITH UNDER = CUR.TEAM_ID
                    CONNECT BY NOCYCLE PRIOR TEAM_ID = UNDER
                )
            );

            SELECT
                COUNT(DISTINCT LT.BPD_INSTANCE_ID )
            INTO
                V_TEAM_ERROR_CAPTURED

            FROM
            (SELECT USER_ID, POSITION_ID, MIN(PATH) PATH
            FROM
                (SELECT B.USER_ID, B.POSITION_ID, A.TEAM_ID, SYS_CONNECT_BY_PATH(A.TEAM_ID,'/') PATH
                FROM MS_TEAM A
                JOIN MS_USER_TEAM B ON A.TEAM_ID = B.TEAM_ID
                CONNECT BY PRIOR A.TEAM_ID = A.UNDER)
                GROUP BY USER_ID, POSITION_ID) T
             JOIN
                    LSW_USR_XREF LU
                ON T.USER_ID = LU.USER_NAME
             JOIN
                    DHB_LSW_TASK LT

                ON  LU.USER_ID = LT.USER_ID
             JOIN ORIG_APPLICATION_GROUP OG
             ON OG.INSTANT_ID = LT.BPD_INSTANCE_ID
                        LEFT JOIN
                            DHB_COMPARISON_DATA OCD
                        ON
                            OCD.APPLICATION_GROUP_ID = OG.APPLICATION_GROUP_ID
                            AND T.USER_ID = OCD.CREATE_BY
            LEFT JOIN
                            ORIG_PERSONAL_INFO OP
                        ON
                            OP.APPLICATION_GROUP_ID = OG.APPLICATION_GROUP_ID
                        LEFT JOIN
                            INC_INFO INC
                        ON
                            INC.PERSONAL_ID = OP.PERSONAL_ID
                            AND INC.CREATE_BY = T.USER_ID

             WHERE  INSTR(T.PATH,CUR.TEAM_ID) > 0
             AND (OCD.QUANTITY > 0 OR INC.COMPARE_FLAG = 'W');

        END IF;

        IF V_TOTAL_TEAM_OUTPUT_APP_MONTH = 0 THEN
            V_TEAM_QUALITY := 0;
        ELSE
            V_TEAM_QUALITY := ( V_TOTAL_TEAM_OUTPUT_APP_MONTH - V_TEAM_ERROR_CAPTURED)*100 /
            V_TOTAL_TEAM_OUTPUT_APP_MONTH ;
        END IF;


        INSERT
        INTO
            DHB_SUMMARY_DATA
            (
                SUMMARY_ID,
                POSITION_LEVEL ,
                DHB_TYPE ,
                DHB_OWNER ,
                DHB_GROUP,
                DHP_SEQ,
                VALUE1 ,
                DESCRIPTION1,
                VALUE2,
                DESCRIPTION2 ,
                VALUE3,
                DESCRIPTION3 ,
                VALUE4 ,
                DESCRIPTION4,
                CREATE_DATE,
                STATUS
            )
            VALUES
            (
                V_SUMMARY_ID,
                V_POSITION_LEVEL,
                V_DHB_TYPE,
                V_DBH_OWNER,
                V_DBH_GROUP,
                V_DBH_SEQ,
                ROUND(V_TEAM_QUALITY,2),
                '%Near Miss',
                NULL,
                NULL,
                NULL,
                NULL,
                NULL,
                NULL,
                V_APPLICATION_DATE,
                'W'
            );
        COMMIT;


        --%Miss = [(Head of. output application of month ?error from customer of month(Key in value))x100]
        -- /total Head of. output application of month


        IF CUR.POSITION_ID IN (1,2,3) THEN

            IF V_TOTAL_TEAM_OUTPUT_APP_MONTH = 0 THEN
                V_TEAM_QUALITY := 0;
        ELSE
            V_TEAM_QUALITY := ( V_TOTAL_TEAM_OUTPUT_APP_MONTH - V_ERROR_FROM_CUST_PARAM)*100 /
            V_TOTAL_TEAM_OUTPUT_APP_MONTH ;
        END IF;

        SELECT
            DHB_SUMMARY_DATA_PK.NEXTVAL
        INTO
            V_SUMMARY_ID
        FROM
            DUAL ;
        V_DBH_GROUP := 1;
        V_DBH_SEQ :=2;

        INSERT
        INTO
            DHB_SUMMARY_DATA
            (
                SUMMARY_ID,
                POSITION_LEVEL ,
                DHB_TYPE ,
                DHB_OWNER ,
                DHB_GROUP,
                DHP_SEQ,
                VALUE1 ,
                DESCRIPTION1,
                VALUE2,
                DESCRIPTION2 ,
                VALUE3,
                DESCRIPTION3 ,
                VALUE4 ,
                DESCRIPTION4,
                CREATE_DATE,
                STATUS
            )
            VALUES
            (
                V_SUMMARY_ID,
                V_POSITION_LEVEL,
                V_DHB_TYPE,
                V_DBH_OWNER,
                V_DBH_GROUP,
                V_DBH_SEQ,
                ROUND(V_TEAM_QUALITY,2),
                '%Miss',
                NULL,
                NULL,
                NULL,
                NULL,
                NULL,
                NULL,
                V_APPLICATION_DATE,
                'W'
            );
        COMMIT;
        END IF;
        V_OLD_USER_ID := CUR.USER_ID;
        V_OLD_TEAM_ID := CUR.TEAM_ID;


    END LOOP;
END P_GEN_DASHBOARD_04;

PROCEDURE P_GEN_DASHBOARD_05_06
AS
    V_DHB_TYPE VARCHAR2(2) := '05';
    V_POSITION_LEVEL VARCHAR2(3) := 'ALL';
    V_DBH_OWNER VARCHAR2(30) := 'ALL';
    V_DBH_GROUP NUMBER;
    V_DBH_SEQ NUMBER;
    V_SUMMARY_ID NUMBER;
    V_PERCENT_BUFFER NUMBER;
    V_OT_CELL_TODAY NUMBER;
    V_OT_CELL_D_1 NUMBER;
    V_OT_CELL_D_2 NUMBER;
    V_URGENT_D_1 NUMBER;
    V_URGENT_D_2 NUMBER;
    V_APPLICATION_DATE DATE;
    V_INCOMING_APP_URGENT NUMBER  :=0;
    V_URGENT_APP NUMBER :=0;
    V_URGENT_APP2 NUMBER :=0;
    V_ON_HAND_URGENT NUMBER :=0;
    V_OT_HOUR NUMBER :=0;
    V_BASELINE NUMBER :=0;
    V_OT_HOUR_HOLIDAY NUMBER :=0;
    V_OLD_USER_ID VARCHAR2(30);
    V_SCHEDULE_ID NUMBER;
BEGIN
    -- ALL --

    /* no need to delete data in procedure
    DELETE
    FROM
        DHB_SUMMARY_DATA
    WHERE
        DHB_TYPE IN ('05',
                     '06');*/

    SELECT MAX(SCHEDULE_ID) INTO V_SCHEDULE_ID
    FROM DHB_SCHEDULE;
    --OT CALL TODAY--

    SELECT TRUNC(APP_DATE) + (SYSDATE - TRUNC(SYSDATE))
    INTO  V_APPLICATION_DATE
    FROM  APPLICATION_DATE;

    --OT CALL TODAY--
    SELECT  VALUE1/100
    INTO  V_PERCENT_BUFFER
    FROM REPORT_PARAM
    WHERE PARAM_TYPE = 'PERCENT_BUFFER'
    AND PARAM_CODE = 'PERCENT_BUFFER';

    --MANAGER--

    V_OLD_USER_ID := NULL;

--Start Insert to  DHB_TEAM DHB_TYPE = '05'
        --INCOMING_APP_URGENT
        V_DHB_TYPE := '05';
        V_DBH_GROUP := 1;
        V_DBH_SEQ :=1;

       DELETE FROM DHB_TEAM WHERE DHB_TYPE = '05';

          INSERT INTO DHB_TEAM
              (  TEAM_ID
                ,SCHEDULE_ID
                ,DHB_TYPE
                ,DHB_GROUP
                ,DHP_SEQ
                ,INCOMING_APP_URGENT
                ,URGENT_APP_1
                ,URGENT_APP_2
                ,CREATE_DATE)
           SELECT MU.TEAM_ID,V_SCHEDULE_ID,V_DHB_TYPE,V_DBH_GROUP,V_DBH_SEQ,
                COUNT(DISTINCT OG.APPLICATION_GROUP_ID
                ) INCOMING_APP_URGENT ,
                COUNT(DISTINCT
                CASE
                    WHEN TRUNC(OG.SLA_DUE_DATE) = TRUNC(V_APPLICATION_DATE + 1)
                    THEN OG.APPLICATION_GROUP_ID
                    ELSE NULL
                END) URGENT_APP_1 ,
                COUNT(DISTINCT
                CASE
                    WHEN TRUNC(OG.SLA_DUE_DATE) = TRUNC(V_APPLICATION_DATE+ 2)
                    THEN OG.APPLICATION_GROUP_ID
                    ELSE NULL
                END)  URGENT_APP_2 ,
                V_APPLICATION_DATE
                FROM ORIG_APPLICATION_GROUP OG --ON OG.PRIORITY IN (1,2)
                --JOIN LSW_TASK LT ON LT.BPD_INSTANCE_ID = OG.INSTANT_ID
                --JOIN LSW_USR_XREF LU ON LU.USER_ID = LT.USER_ID
                JOIN MS_USER_TEAM MU  ON OG.PRIORITY IN (1,2)  --ON LU.USER_NAME = MU.USER_ID
                JOIN ORIG_IAS.USER_ROLE UR ON MU.USER_ID = UR.USER_NAME
                JOIN ORIG_IAS.ROLE R ON R.ROLE_ID = UR.ROLE_ID
                JOIN  (SELECT
                            JOB_STATE.JOB_STATE,
                            INCOMING.INCOMING_CODE,
                            INCOMING.PARAM_CODE
                        FROM DHB_JOB_STATE  JOB_STATE
                        JOIN (SELECT
                                    SUBSTR (CSV, INSTR (CSV, ',', 1, LEV) + 1, INSTR (CSV, ',', 1, LEV + 1) - INSTR
                                    (CSV, ',', 1, LEV) - 1) INCOMING_CODE,
                                    PARAM_CODE FROM (   SELECT /*+ INDEX(GENERAL_PARAM,IDX03_GENERAL_PARAM) */
                                                                    ',' || PARAM_VALUE2 || ',' CSV,
                                                                      PARAM_CODE
                                                                     FROM  GENERAL_PARAM ),
                                                                    (  SELECT   LEVEL LEV
                                                                        FROM  DUAL CONNECT BY LEVEL <= 100)
                                   WHERE
                                       LEV <= LENGTH (CSV) - LENGTH (REPLACE (CSV, ',')) - 1) INCOMING
                         ON
                             INCOMING.INCOMING_CODE = JOB_STATE.PARAM_CODE
                         WHERE
                             INCOMING.PARAM_CODE LIKE 'WIP_JOBSTATE%') T
                ON (
                             (  T.JOB_STATE = OG.JOB_STATE  AND T.PARAM_CODE = 'WIP_JOBSTATE_' || R.ROLE_NAME )
                       OR  ( R.ROLE_NAME IN ('DE1_1', 'IA', 'DE1_2')
                               OR   EXISTS  ( SELECT 1 FROM DHB_JOB_STATE T
                                                    WHERE T.JOB_STATE = OG.JOB_STATE
                                                    AND T.PARAM_CODE LIKE 'WIP_JOBSTATE_FU'
                                                    AND OG.PREV_JOB_STATE IN (SELECT JOB_STATE
                                                                                                 FROM DHB_JOB_STATE T
                                                                                                 WHERE PARAM_CODE IN ( 'WIP_JOBSTATE_IA' ,
                                                                                                                                   'WIP_JOBSTATE_DE1_1',
                                                                                                                                   'WIP_JOBSTATE_DE1_2')
                                                                                                )
                                                   )
                                 )
                      )

                 AND
                    (     ( R.ROLE_NAME != 'VT'  AND R.ROLE_NAME !='CA')
                    OR  ( R.ROLE_NAME != 'VT' AND EXISTS  (  SELECT   1
                                                                                    FROM  ORIG_APPLICATION AP
                                                                                    WHERE  AP.APPLICATION_GROUP_ID = OG.APPLICATION_GROUP_ID
                                                                                    AND ( AP.LIFE_CYCLE > 1   OR  OG.LAST_DECISION = 'RE')
                                                                                  )
                           )
                       )
              GROUP BY MU.TEAM_ID,V_SCHEDULE_ID,V_DHB_TYPE,V_DBH_GROUP,V_DBH_SEQ,V_APPLICATION_DATE;

              --
              INSERT INTO DHB_TEAM
              (  TEAM_ID
                ,SCHEDULE_ID
                ,DHB_TYPE
                ,DHB_GROUP
                ,DHP_SEQ
                ,ON_HAND_URGENT
                ,CREATE_DATE)
                SELECT TEAM_ID,V_SCHEDULE_ID,V_DHB_TYPE,V_DBH_GROUP,V_DBH_SEQ,
                                COUNT(DISTINCT APPLICATION_GROUP_ID),
                                V_APPLICATION_DATE
                    FROM
                    (
                    SELECT DISTINCT OG.APPLICATION_GROUP_ID,U.TEAM_ID
                    FROM MS_USER_TEAM U
                    JOIN ORIG_IAS.USER_ROLE UR ON U.USER_ID = UR.USER_NAME
                    JOIN ORIG_IAS.ROLE R  ON   R.ROLE_ID = UR.ROLE_ID
                    JOIN DHB_JOB_STATE JST  ON JST.PARAM_CODE = 'WIP_JOBSTATE_' || R.ROLE_NAME
                    --T.PARAM_CODE LIKE 'WIP_JOBSTATE_%'
                    JOIN ORIG_APPLICATION_GROUP OG   ON  JST.JOB_STATE = OG.JOB_STATE
                                                                            AND OG.PRIORITY IN (1,2)
                    JOIN  LSW_TASK LT  ON  LT.BPD_INSTANCE_ID = OG.INSTANT_ID
                                                    AND LT.STATUS = 12
                                                    AND LT.TASK_ID =   (SELECT  MAX(TASK_ID)
                                                                                    FROM LSW_TASK X
                                                                                    WHERE X.BPD_INSTANCE_ID = LT.BPD_INSTANCE_ID)
--                    --WHERE
--                    --U.POSITION_ID IN (1,2,3,4)
--                    --AND
--                    --U.USER_ID = CUR.USER_ID
--                    --JOIN
--                    --    MS_USER_TEAM U
--                   -- ON
--                    --    U.USER_ID = UR.USER_NAME
--                   -- GROUP BY U.USER_ID;
--
--                    MINUS
--                        (
--                            SELECT DISTINCT OG1.APPLICATION_GROUP_ID,U.TEAM_ID
--                            FROM   MS_USER_TEAM U
--                            JOIN  ORIG_IAS.USER_ROLE UR ON U.USER_ID = UR.USER_NAME
--                            JOIN  ORIG_IAS.ROLE R  ON  R.ROLE_ID = UR.ROLE_ID
--                            LEFT JOIN  REPORT_PARAM RP_BS  ON RP_BS.PARAM_TYPE = 'BASELINE_APP'
--                                                                                 AND RP_BS.PARAM_CODE = R.ROLE_NAME
--                            LEFT JOIN  REPORT_PARAM RP_OT ON  RP_OT.PARAM_TYPE = 'OT_HOUR_WEEKDAY'
--                                                                                 AND RP_OT.PARAM_CODE = R.ROLE_NAME
--                            LEFT JOIN REPORT_PARAM RP_OTH ON  RP_OTH.PARAM_TYPE = 'OT_HOUR_HOLIDAY'
--                                                                                AND RP_OTH.PARAM_CODE = R.ROLE_NAME
--                            JOIN ORIG_APPLICATION_GROUP OG1 ON OG1.PRIORITY IN (1,2)
--                                            AND  (      (  R.ROLE_NAME != 'VT' AND R.ROLE_NAME !='CA')
--                                                     OR  (  R.ROLE_NAME != 'VT'  AND EXISTS    (  SELECT 1 FROM ORIG_APPLICATION AP
--                                                                                                                         WHERE  AP.APPLICATION_GROUP_ID = OG1.APPLICATION_GROUP_ID
--                                                                                                                         AND ( LIFE_CYCLE > 1 OR  LAST_DECISION = 'RE')
--                                                                                                                          )
--                                                             )
--                                                      )
--                            JOIN  (  SELECT
--                                        JOB_STATE.JOB_STATE,
--                                        INCOMING.INCOMING_CODE,
--                                        INCOMING.PARAM_CODE
--                                    FROM DHB_JOB_STATE
--                                         JOB_STATE
--                                    JOIN
--                                        (  SELECT
--                                                SUBSTR (CSV, INSTR (CSV, ',', 1, LEV) + 1, INSTR (CSV, ',', 1, LEV + 1)
--                                                - INSTR (CSV, ',', 1, LEV) - 1) INCOMING_CODE,
--                                                PARAM_CODE
--                                            FROM
--                                                (  SELECT ',' || PARAM_VALUE2 || ',' CSV,
--                                                     PARAM_CODE
--                                                    FROM  GENERAL_PARAM ),
--                                                (  SELECT  LEVEL LEV
--                                                    FROM  DUAL CONNECT BY LEVEL <= 100)
--                                            WHERE
--                                                LEV <= LENGTH (CSV) - LENGTH (REPLACE (CSV, ',')) - 1) INCOMING
--                                     ON
--                                        INCOMING.INCOMING_CODE = JOB_STATE.PARAM_CODE
--                                    WHERE
--                                        INCOMING.PARAM_CODE LIKE 'WIP_JOBSTATE%') T
--                            ON  (   ( T.JOB_STATE = OG1.JOB_STATE AND T.PARAM_CODE = 'WIP_JOBSTATE_' || R.ROLE_NAME )
--                                OR  (  R.ROLE_NAME IN ('DE1_1', 'IA',  'DE1_2')
--                                         AND EXISTS  ( SELECT 1  FROM DHB_JOB_STATE T
--                                                                WHERE  T.PARAM_CODE LIKE 'WIP_JOBSTATE_FU'
--                                                                AND T.JOB_STATE = OG1.JOB_STATE
--                                                                AND OG1.PREV_JOB_STATE IN   ( SELECT JOB_STATE FROM DHB_JOB_STATE
--                                                                                                                  WHERE PARAM_CODE IN ( 'WIP_JOBSTATE_IA' ,
--                                                                                                                                                         'WIP_JOBSTATE_DE1_1',
--                                                                                                                                                        'WIP_JOBSTATE_DE1_2')
--                                                                                                                 )
--                                                                )
--                                          )
--                                      )
--                            )
                            ) GROUP BY TEAM_ID,V_SCHEDULE_ID,V_DHB_TYPE,V_DBH_GROUP,V_DBH_SEQ,V_APPLICATION_DATE
                            ;
--End Insert to  DHB_TEAM DHB_TYPE = '05'

    FOR CUR IN (SELECT DISTINCT
                            USER_ID ,
                            (POSITION_ID) POSITION_ID
                        FROM MS_USER_TEAM
                        WHERE POSITION_ID IN (1,2, 3,4))
    LOOP

             V_INCOMING_APP_URGENT := 0;
             V_URGENT_D_1                  := 0;
             V_URGENT_D_2                  := 0;
             V_ON_HAND_URGENT          := 0;

            SELECT SUM(NVL(INCOMING_APP_URGENT,0))
                        ,SUM(NVL(URGENT_APP_1,0))
                        ,SUM(NVL(URGENT_APP_2,0))
                        ,SUM(NVL(ON_HAND_URGENT,0))
            INTO
                     V_INCOMING_APP_URGENT
                    ,V_URGENT_D_1
                    ,V_URGENT_D_2
                    ,V_ON_HAND_URGENT
            FROM DHB_TEAM
            --WHERE SCHEDULE_ID = V_SCHEDULE_ID
            WHERE DHB_TYPE = '05'
            AND DHP_SEQ = 1
            AND EXISTS (SELECT 1 FROM  MS_USER_TEAM MU
                        JOIN
                            ( SELECT  T5.TEAM_ID T5 ,
                                    MT5.POSITION_ID,
                                    MT5.USER_ID ,
                                    T4.TEAM_ID T4,
                                    T3.TEAM_ID T3 ,
                                    T2.TEAM_ID T2,
                                    T1.TEAM_ID T1
                                FROM  MS_TEAM T5
                                JOIN MS_USER_TEAM MT5 ON  T5.TEAM_ID = MT5.TEAM_ID
                                LEFT JOIN  MS_TEAM T4 ON  T4.TEAM_ID = T5.UNDER
                                LEFT JOIN  MS_TEAM T3 ON T3.TEAM_ID = T4.UNDER
                                LEFT JOIN MS_TEAM T2  ON T2.TEAM_ID = T3.UNDER
                                LEFT JOIN   MS_TEAM T1 ON T1.TEAM_ID = T2.UNDER ) T
                        ON
                            ( MU.TEAM_ID = T.T5
                            OR  MU.TEAM_ID = T4
                            OR  MU.TEAM_ID = T3
                            OR  MU.TEAM_ID = T2
                            OR  MU.TEAM_ID = T1)
                       WHERE MU.USER_ID = CUR.USER_ID
                       --AND MU.TEAM_ID = DHB_TEAM.TEAM_ID);
                       AND T.T5 = DHB_TEAM.TEAM_ID);


           SELECT
                MAX(NVL(RP_OT.VALUE1,0))   OT_HOUR ,
                MAX(NVL(RP_BS.VALUE1,0))   BASELINE ,
                MAX(NVL(RP_OTH.VALUE1,0)) OT_HOUR_HOLIDAY
            INTO
                V_OT_HOUR,
                V_BASELINE,
                V_OT_HOUR_HOLIDAY
            FROM  MS_USER_TEAM MU
            JOIN  ORIG_IAS.USER_ROLE UR  ON  MU.USER_ID = UR.USER_NAME
            JOIN  ORIG_IAS.ROLE R ON R.ROLE_ID = UR.ROLE_ID
            LEFT JOIN  REPORT_PARAM RP_BS  ON  RP_BS.PARAM_TYPE = 'BASELINE_APP'
                                                                AND RP_BS.PARAM_CODE = R.ROLE_NAME
            LEFT JOIN REPORT_PARAM RP_OT ON RP_OT.PARAM_TYPE = 'OT_HOUR_WEEKDAY'
                                                                AND RP_OT.PARAM_CODE = R.ROLE_NAME
            LEFT JOIN  REPORT_PARAM RP_OTH ON RP_OTH.PARAM_TYPE = 'OT_HOUR_HOLIDAY'
                                                                AND RP_OTH.PARAM_CODE = R.ROLE_NAME
             WHERE  (     ( R.ROLE_NAME != 'VT'  AND R.ROLE_NAME !='CA')
                         OR  ( R.ROLE_NAME != 'VT' )
                          )
            AND EXISTS
                (SELECT  * FROM  MS_USER_TEAM U
                    JOIN
                        (
                            SELECT
                                T5.TEAM_ID T5 ,
                                MT5.POSITION_ID,
                                MT5.USER_ID ,
                                T4.TEAM_ID T4,
                                T3.TEAM_ID T3 ,
                                T2.TEAM_ID T2,
                                T1.TEAM_ID T1
                            FROM  MS_TEAM T5
                            JOIN  MS_USER_TEAM MT5  ON  T5.TEAM_ID = MT5.TEAM_ID
                            LEFT JOIN  MS_TEAM T4  ON  T4.UNDER = T5.TEAM_ID
                            LEFT JOIN MS_TEAM T3  ON  T3.UNDER = T4.TEAM_ID
                            LEFT JOIN  MS_TEAM T2  ON T2.UNDER = T3.TEAM_ID
                            LEFT JOIN  MS_TEAM T1  ON T1.UNDER = T2.TEAM_ID ) T
                    ON
                        (  U.TEAM_ID = T.T5
                        OR  U.TEAM_ID = T4
                        OR  U.TEAM_ID = T3
                        OR  U.TEAM_ID = T2
                        OR  U.TEAM_ID = T1)
                    WHERE
                        T.USER_ID = CUR.USER_ID
                    AND U.USER_ID = MU.USER_ID )
            ;

/*
        SELECT
            COUNT(DISTINCT OG.APPLICATION_GROUP_ID
            ) INCOMING_APP ,
            COUNT(DISTINCT
            CASE
                WHEN TRUNC(OG.SLA_DUE_DATE) = TRUNC(V_APPLICATION_DATE + 1)
                THEN OG.APPLICATION_GROUP_ID
                ELSE NULL
            END) URGENT_APP ,
            COUNT(DISTINCT
            CASE
                WHEN TRUNC(OG.SLA_DUE_DATE) = TRUNC(V_APPLICATION_DATE + 2)
                THEN OG.APPLICATION_GROUP_ID
                ELSE NULL
            END)               URGENT_APP_2 ,
            MAX(RP_OT.VALUE1)  OT_HOUR ,
            MAX(RP_BS.VALUE1)  BASELINE ,
            MAX(RP_OTH.VALUE1) OT_HOUR_HOLIDAY
        INTO
            V_INCOMING_APP_URGENT,
            V_URGENT_D_1,
            V_URGENT_D_2,
            V_OT_HOUR,
            V_BASELINE,
            V_OT_HOUR_HOLIDAY
        FROM
            MS_USER_TEAM U
        JOIN
           (SELECT DISTINCT USER_ID, POSITION_ID, PATH --MAX(PATH) PATH
            FROM
                        (SELECT B.USER_ID, B.POSITION_ID, A.TEAM_ID, SYS_CONNECT_BY_PATH(A.TEAM_ID,'/') PATH
                        FROM MS_TEAM A
                        JOIN MS_USER_TEAM B ON A.TEAM_ID = B.TEAM_ID
                        CONNECT BY PRIOR A.TEAM_ID = A.UNDER )
            --GROUP BY USER_ID, POSITION_ID
            ) T
        ON
            INSTR(T.PATH,U.TEAM_ID) > 0
            AND U.POSITION_ID IN (1,2, 3,4,5)
        JOIN
            ORIG_IAS.USER_ROLE UR
        ON
            ( UR.USER_NAME = T.USER_ID )
        JOIN
            ORIG_IAS.ROLE R
        ON
            R.ROLE_ID = UR.ROLE_ID
        LEFT JOIN
            REPORT_PARAM RP_BS
        ON
            RP_BS.PARAM_TYPE = 'BASELINE_APP'
        AND RP_BS.PARAM_CODE = R.ROLE_NAME
        LEFT JOIN
            REPORT_PARAM RP_OT
        ON
            RP_OT.PARAM_TYPE = 'OT_HOUR_WEEKDAY'
        AND RP_OT.PARAM_CODE = R.ROLE_NAME
        LEFT JOIN
            REPORT_PARAM RP_OTH
        ON
            RP_OTH.PARAM_TYPE = 'OT_HOUR_HOLIDAY'
        AND RP_OTH.PARAM_CODE = R.ROLE_NAME
        JOIN ORIG_APPLICATION_GROUP OG
        ON OG.PRIORITY IN (1,2)
        JOIN
            (
                SELECT
                    JOB_STATE.JOB_STATE,
                    INCOMING.INCOMING_CODE,
                    INCOMING.PARAM_CODE
                FROM DHB_JOB_STATE  JOB_STATE
                JOIN
                    (
                        SELECT
                            SUBSTR (CSV, INSTR (CSV, ',', 1, LEV) + 1, INSTR (CSV, ',', 1, LEV + 1) - INSTR
                            (CSV, ',', 1, LEV) - 1) INCOMING_CODE,
                            PARAM_CODE
                        FROM
                            (   SELECT /*+ INDEX(GENERAL_PARAM,IDX03_GENERAL_PARAM) */
/*
                                    ',' || PARAM_VALUE2 || ',' CSV,
                                    PARAM_CODE
                                FROM  GENERAL_PARAM ),
                            (  SELECT   LEVEL LEV
                                FROM  DUAL CONNECT BY LEVEL <= 100)
                        WHERE
                            LEV <= LENGTH (CSV) - LENGTH (REPLACE (CSV, ',')) - 1) INCOMING
                ON
                    INCOMING.INCOMING_CODE = JOB_STATE.PARAM_CODE
                WHERE
                    INCOMING.PARAM_CODE LIKE 'WIP_JOBSTATE%') T
        ON
            ( (  T.JOB_STATE = OG.JOB_STATE
                AND T.PARAM_CODE = 'WIP_JOBSTATE_' || R.ROLE_NAME )
            OR  ( R.ROLE_NAME IN ('DE1_1',
                                                'IA',
                                                'DE1_2')
                OR  EXISTS
                    ( SELECT 1
                        FROM DHB_JOB_STATE T
                        WHERE
                            T.JOB_STATE = OG.JOB_STATE
                            AND T.PARAM_CODE LIKE 'WIP_JOBSTATE_FU'
                        AND OG.PREV_JOB_STATE IN
                        (SELECT JOB_STATE
                         FROM DHB_JOB_STATE T
                         WHERE PARAM_CODE IN ( 'WIP_JOBSTATE_IA' ,
                                                           'WIP_JOBSTATE_DE1_1',
                                                           'WIP_JOBSTATE_DE1_2'))
                            )))
        WHERE U.USER_ID = CUR.USER_ID
         AND
            (     ( R.ROLE_NAME != 'VT'  AND R.ROLE_NAME !='CA')
            OR  ( R.ROLE_NAME != 'VT'
                AND EXISTS  (  SELECT   1
                                        FROM  ORIG_APPLICATION AP
                                        WHERE  AP.APPLICATION_GROUP_ID = OG.APPLICATION_GROUP_ID
                                        AND ( AP.LIFE_CYCLE > 1
                                            OR  OG.LAST_DECISION = 'RE') )) ) ;
*/
        /*SELECT
            COUNT(DISTINCT
            CASE
                WHEN OG.PRIORITY IN (1,2)
                THEN OG.APPLICATION_GROUP_ID
                ELSE NULL
            END) INCOMING_APP ,
            COUNT(DISTINCT
            CASE
                WHEN OG.PRIORITY IN (1,2)
                AND TRUNC(OG.SLA_DUE_DATE) = TRUNC(V_APPLICATION_DATE + 1)
                THEN OG.APPLICATION_GROUP_ID
                ELSE NULL
            END) URGENT_APP ,
            COUNT(DISTINCT
            CASE
                WHEN OG.PRIORITY IN (1,2)
                AND TRUNC(OG.SLA_DUE_DATE) = TRUNC(V_APPLICATION_DATE + 2)
                THEN OG.APPLICATION_GROUP_ID
                ELSE NULL
            END)               URGENT_APP_2 ,
            MAX(RP_OT.VALUE1)  OT_HOUR ,
            MAX(RP_BS.VALUE1)  BASELINE ,
            MAX(RP_OTH.VALUE1) OT_HOUR_HOLIDAY
        INTO
            V_INCOMING_APP_URGENT,
            V_URGENT_D_1,
            V_URGENT_D_2,
            V_OT_HOUR,
            V_BASELINE,
            V_OT_HOUR_HOLIDAY
        FROM
            ORIG_APPLICATION_GROUP OG
            --JOIN LSW_TASK LT ON LT.BPD_INSTANCE_ID = OG.INSTANT_ID
            --JOIN LSW_USR_XREF LU ON LU.USER_ID = LT.USER_ID
        JOIN
            MS_USER_TEAM U
        ON
            U.USER_ID = CUR.USER_ID
        JOIN
            (
                SELECT
                    T5.TEAM_ID T5 ,
                    MT5.POSITION_ID,
                    MT5.USER_ID ,
                    T4.TEAM_ID T4,
                    T3.TEAM_ID T3 ,
                    T2.TEAM_ID T2,
                    T1.TEAM_ID T1
                FROM
                    MS_TEAM T5
                JOIN
                    MS_USER_TEAM MT5
                ON
                    T5.TEAM_ID = MT5.TEAM_ID
                LEFT JOIN
                    MS_TEAM T4
                ON
                    T4.TEAM_ID = T5.UNDER
                LEFT JOIN
                    MS_TEAM T3
                ON
                    T3.TEAM_ID = T4.UNDER
                LEFT JOIN
                    MS_TEAM T2
                ON
                    T2.TEAM_ID = T3.UNDER
                LEFT JOIN
                    MS_TEAM T1
                ON
                    T1.TEAM_ID = T2.UNDER ) T
        ON
            (
                U.TEAM_ID = T.T5
            OR  U.TEAM_ID = T4
            OR  U.TEAM_ID = T3
            OR  U.TEAM_ID = T2
            OR  U.TEAM_ID = T1)
            AND U.USER_ID = T.USER_ID
        JOIN
            ORIG_IAS.USER_ROLE UR
        ON
            (
                UR.USER_NAME = T.USER_ID )
        JOIN
            ORIG_IAS.ROLE R
        ON
            R.ROLE_ID = UR.ROLE_ID
        LEFT JOIN
            REPORT_PARAM RP_BS
        ON
            RP_BS.PARAM_TYPE = 'BASELINE_APP'
        AND RP_BS.PARAM_CODE = R.ROLE_NAME
        LEFT JOIN
            REPORT_PARAM RP_OT
        ON
            RP_OT.PARAM_TYPE = 'OT_HOUR_WEEKDAY'
        AND RP_OT.PARAM_CODE = R.ROLE_NAME
        LEFT JOIN
            REPORT_PARAM RP_OTH
        ON
            RP_OTH.PARAM_TYPE = 'OT_HOUR_HOLIDAY'
        AND RP_OTH.PARAM_CODE = R.ROLE_NAME
        JOIN
            (
                SELECT
                    JOB_STATE.JOB_STATE,
                    INCOMING.INCOMING_CODE,
                    INCOMING.PARAM_CODE
                FROM
                    (
                        SELECT
                            SUBSTR (CSV, INSTR (CSV, ',', 1, LEV) + 1, INSTR (CSV, ',', 1, LEV + 1) - INSTR
                            (CSV, ',', 1, LEV) - 1) JOB_STATE,
                            PARAM_CODE
                        FROM
                            (
                                SELECT
                                    ',' || PARAM_VALUE || ',' CSV,
                                    PARAM_CODE
                                FROM
                                    GENERAL_PARAM ),
                            (
                                SELECT
                                    LEVEL LEV
                                FROM
                                    DUAL CONNECT BY LEVEL <= 100)
                        WHERE
                            LEV <= LENGTH (CSV) - LENGTH (REPLACE (CSV, ',')) - 1) JOB_STATE
                JOIN
                    (
                        SELECT
                            SUBSTR (CSV, INSTR (CSV, ',', 1, LEV) + 1, INSTR (CSV, ',', 1, LEV + 1) - INSTR
                            (CSV, ',', 1, LEV) - 1) INCOMING_CODE,
                            PARAM_CODE
                        FROM
                            (
                                SELECT
                                    ',' || PARAM_VALUE2 || ',' CSV,
                                    PARAM_CODE
                                FROM
                                    GENERAL_PARAM ),
                            (
                                SELECT
                                    LEVEL LEV
                                FROM
                                    DUAL CONNECT BY LEVEL <= 100)
                        WHERE
                            LEV <= LENGTH (CSV) - LENGTH (REPLACE (CSV, ',')) - 1) INCOMING
                ON
                    INCOMING.INCOMING_CODE = JOB_STATE.PARAM_CODE
                WHERE
                    INCOMING.PARAM_CODE LIKE 'WIP_JOBSTATE%') T
        ON
            ( (
                    T.JOB_STATE = OG.JOB_STATE
                AND T.PARAM_CODE = 'WIP_JOBSTATE_' || R.ROLE_NAME )
            OR  (
                    R.ROLE_NAME IN ('DE1_1',
                                    'IA',
                                    'DE1_2')
                OR  EXISTS
                    (
                        SELECT
                            1
                        FROM
                            (
                                SELECT
                                    SUBSTR (CSV, INSTR (CSV, ',', 1, LEV) + 1, INSTR (CSV, ',', 1, LEV + 1)
                                    - INSTR (CSV, ',', 1, LEV) - 1) JOB_STATE,
                                    PARAM_CODE
                                FROM
                                    (
                                        SELECT
                                            ',' || PARAM_VALUE || ',' CSV,
                                            PARAM_CODE
                                        FROM
                                            GENERAL_PARAM
                                        WHERE
                                            PARAM_CODE LIKE 'WIP_JOBSTATE_FU' ),
                                    (
                                        SELECT
                                            LEVEL LEV
                                        FROM
                                            DUAL CONNECT BY LEVEL <= 100)
                                WHERE
                                    LEV <= LENGTH (CSV) - LENGTH (REPLACE (CSV, ',')) - 1) T
                        WHERE
                            T.JOB_STATE = OG.JOB_STATE
                        AND OG.PREV_JOB_STATE IN
                            (
                                SELECT
                                    SUBSTR (CSV, INSTR (CSV, ',', 1, LEV) + 1, INSTR (CSV, ',', 1, LEV + 1)
                                    - INSTR (CSV, ',', 1, LEV) - 1) JOB_STATE
                                FROM
                                    (
                                        SELECT
                                            ',' || PARAM_VALUE || ',' CSV,
                                            PARAM_CODE
                                        FROM
                                            GENERAL_PARAM
                                        WHERE
                                            PARAM_CODE IN ( 'WIP_JOBSTATE_IA' ,
                                                           'WIP_JOBSTATE_DE1_1',
                                                           'WIP_JOBSTATE_DE1_2')),
                                    (
                                        SELECT
                                            LEVEL LEV
                                        FROM
                                            DUAL CONNECT BY LEVEL <= 100)
                                WHERE
                                    LEV <= LENGTH (CSV) - LENGTH (REPLACE (CSV, ',')) - 1))))
        WHERE
            ((
                    R.ROLE_NAME != 'VT'
                AND R.ROLE_NAME !='CA')
            OR  (
                    R.ROLE_NAME != 'VT'
                AND EXISTS
                    (
                        SELECT
                            1
                        FROM
                            ORIG_APPLICATION AP
                        WHERE
                            AP.APPLICATION_GROUP_ID = OG.APPLICATION_GROUP_ID
                        AND (
                                LIFE_CYCLE > 1
                            OR  LAST_DECISION = 'RE') )) ) ; */

        /*select  count(distinct case when og.priority in (1,2) then og.INSTANT_ID else null end)
        into  v_on_hand_urgent
        from orig_application_group og
        join LSW_TASK lt on LT.BPD_INSTANCE_ID = OG.INSTANT_ID
        join LSW_USR_XREF lu on lu.USER_ID = LT.USER_ID
        where LT.STATUS = '12'
        and  exists(
        select * from ms_user_team mu join
        (
        select t5.under,t5.team_id t5 ,mt5.position_id,mt5.user_id ,t4.team_id t4,t3.team_id t3 ,t2.team_id
        t2,t1.team_id t1
        from ms_user_team mt5
        join ms_team t5 on t5.team_id = mt5.team_id
        left join ms_team t4 on t4.under = t5.team_id
        left join ms_team t3 on t3.under = t4.team_id
        left join ms_team t2 on t2.under = t3.team_id
        left join ms_team t1 on t1.under = t2.team_id
        where  mt5.user_id = cur.user_id
        ) t on (mu.team_id  = t.t5 or mu.team_id = t4 or mu.team_id = t3 or mu.team_id = t2 or mu.team_id =
        t1)
        where mu.user_id = lu.user_name ); */
/*
SELECT
        COUNT(DISTINCT APPLICATION_GROUP_ID)
        INTO
            V_ON_HAND_URGENT
        FROM
        (
        SELECT DISTINCT OG.APPLICATION_GROUP_ID
        FROM
            MS_USER_TEAM U
        JOIN
           (SELECT USER_ID, POSITION_ID, MIN(PATH) PATH
            FROM
            (SELECT B.USER_ID, B.POSITION_ID, A.TEAM_ID, SYS_CONNECT_BY_PATH(A.TEAM_ID,'/') PATH
            FROM MS_TEAM A
            JOIN MS_USER_TEAM B ON A.TEAM_ID = B.TEAM_ID
            CONNECT BY PRIOR A.TEAM_ID = A.UNDER)
            GROUP BY USER_ID, POSITION_ID) T
        ON
            INSTR(T.PATH,U.TEAM_ID) > 0
            AND U.USER_ID = CUR.USER_ID
        JOIN
            ORIG_IAS.USER_ROLE UR
        ON
            (
                UR.USER_NAME = T.USER_ID )
        JOIN
            ORIG_IAS.ROLE R
        ON
            R.ROLE_ID = UR.ROLE_ID

        JOIN DHB_JOB_STATE JST
        ON
        --T.PARAM_CODE LIKE 'WIP_JOBSTATE_%'
        --AND
        JST.PARAM_CODE = 'WIP_JOBSTATE_' || R.ROLE_NAME
        JOIN ORIG_APPLICATION_GROUP OG
        ON
            JST.JOB_STATE = OG.JOB_STATE
            AND OG.PRIORITY IN (1,2)
        JOIN  LSW_TASK LT
                ON
                    LT.BPD_INSTANCE_ID = OG.INSTANT_ID
                AND LT.STATUS = 12
                AND LT.TASK_ID =
                    (
                        SELECT
                            MAX(TASK_ID)
                        FROM
                            LSW_TASK X
                        WHERE
                            X.BPD_INSTANCE_ID = LT.BPD_INSTANCE_ID)
        WHERE
        --U.POSITION_ID IN (1,2,3,4)

        --AND
        U.USER_ID = CUR.USER_ID
        --JOIN
        --    MS_USER_TEAM U
       -- ON
        --    U.USER_ID = UR.USER_NAME

       -- GROUP BY U.USER_ID;
        MINUS
            (
                SELECT DISTINCT OG1.APPLICATION_GROUP_ID
                FROM   MS_USER_TEAM U
                JOIN
                    (SELECT USER_ID, POSITION_ID, MIN(PATH) PATH
                    FROM
                    (SELECT B.USER_ID, B.POSITION_ID, A.TEAM_ID, SYS_CONNECT_BY_PATH(A.TEAM_ID,'/') PATH
                    FROM MS_TEAM A
                    JOIN MS_USER_TEAM B ON A.TEAM_ID = B.TEAM_ID
                    CONNECT BY PRIOR A.TEAM_ID = A.UNDER)
                    GROUP BY USER_ID, POSITION_ID) T
                ON
                    INSTR(T.PATH,U.TEAM_ID) > 0
                JOIN
                    ORIG_IAS.USER_ROLE UR
                ON
                    (  UR.USER_NAME = T.USER_ID )
                JOIN
                    ORIG_IAS.ROLE R
                ON
                    R.ROLE_ID = UR.ROLE_ID
                LEFT JOIN
                    REPORT_PARAM RP_BS
                ON
                    RP_BS.PARAM_TYPE = 'BASELINE_APP'
                AND RP_BS.PARAM_CODE = R.ROLE_NAME
                LEFT JOIN
                    REPORT_PARAM RP_OT
                ON
                    RP_OT.PARAM_TYPE = 'OT_HOUR_WEEKDAY'
                AND RP_OT.PARAM_CODE = R.ROLE_NAME
                LEFT JOIN
                    REPORT_PARAM RP_OTH
                ON
                    RP_OTH.PARAM_TYPE = 'OT_HOUR_HOLIDAY'
                AND RP_OTH.PARAM_CODE = R.ROLE_NAME
                JOIN ORIG_APPLICATION_GROUP OG1 ON OG1.PRIORITY IN (1,2)
                AND
                    ((
                            R.ROLE_NAME != 'VT'
                        AND R.ROLE_NAME !='CA')
                    OR  (
                            R.ROLE_NAME != 'VT'
                        AND EXISTS
                            (
                                SELECT
                                    1
                                FROM
                                    ORIG_APPLICATION AP
                                WHERE
                                    AP.APPLICATION_GROUP_ID = OG1.APPLICATION_GROUP_ID
                                AND (
                                        LIFE_CYCLE > 1
                                    OR  LAST_DECISION = 'RE') )) )
                JOIN
                    (
                        SELECT
                            JOB_STATE.JOB_STATE,
                            INCOMING.INCOMING_CODE,
                            INCOMING.PARAM_CODE
                        FROM DHB_JOB_STATE
                             JOB_STATE
                        JOIN
                            (
                                SELECT
                                    SUBSTR (CSV, INSTR (CSV, ',', 1, LEV) + 1, INSTR (CSV, ',', 1, LEV + 1)
                                    - INSTR (CSV, ',', 1, LEV) - 1) INCOMING_CODE,
                                    PARAM_CODE
                                FROM
                                    (
                                        SELECT
                                            ',' || PARAM_VALUE2 || ',' CSV,
                                            PARAM_CODE
                                        FROM
                                            GENERAL_PARAM ),
                                    (
                                        SELECT
                                            LEVEL LEV
                                        FROM
                                            DUAL CONNECT BY LEVEL <= 100)
                                WHERE
                                    LEV <= LENGTH (CSV) - LENGTH (REPLACE (CSV, ',')) - 1) INCOMING
                        ON
                            INCOMING.INCOMING_CODE = JOB_STATE.PARAM_CODE
                        WHERE
                            INCOMING.PARAM_CODE LIKE 'WIP_JOBSTATE%') T
                ON
                    ( (
                            T.JOB_STATE = OG1.JOB_STATE
                        AND T.PARAM_CODE = 'WIP_JOBSTATE_' || R.ROLE_NAME )
                    OR  (
                            R.ROLE_NAME IN ('DE1_1',
                                            'IA',
                                            'DE1_2')
                        AND EXISTS
                            (
                                SELECT
                                    1
                                FROM DHB_JOB_STATE T
                                WHERE
                                    T.PARAM_CODE LIKE 'WIP_JOBSTATE_FU' AND
                                    T.JOB_STATE = OG1.JOB_STATE
                                AND OG1.PREV_JOB_STATE IN
                                    ( SELECT JOB_STATE FROM DHB_JOB_STATE
                                      WHERE PARAM_CODE IN ( 'WIP_JOBSTATE_IA' ,
                                                                   'WIP_JOBSTATE_DE1_1',
                                                                   'WIP_JOBSTATE_DE1_2')
                                        ))))
                WHERE
               U.USER_ID = CUR.USER_ID

                ));
      */
        /*SELECT
            COUNT(DISTINCT OG.APPLICATION_GROUP_ID)
        INTO
            V_ON_HAND_URGENT
        FROM
            ORIG_APPLICATION_GROUP OG
        JOIN
            MS_USER_TEAM U
        ON
            U.USER_ID = CUR.USER_ID
        JOIN
            (
                SELECT
                    T5.TEAM_ID T5 ,
                    MT5.POSITION_ID,
                    MT5.USER_ID ,
                    T4.TEAM_ID T4,
                    T3.TEAM_ID T3 ,
                    T2.TEAM_ID T2,
                    T1.TEAM_ID T1
                FROM
                    MS_TEAM T5
                JOIN
                    MS_USER_TEAM MT5
                ON
                    T5.TEAM_ID = MT5.TEAM_ID
                LEFT JOIN
                    MS_TEAM T4
                ON
                    T4.TEAM_ID = T5.UNDER
                LEFT JOIN
                    MS_TEAM T3
                ON
                    T3.TEAM_ID = T4.UNDER
                LEFT JOIN
                    MS_TEAM T2
                ON
                    T2.TEAM_ID = T3.UNDER
                LEFT JOIN
                    MS_TEAM T1
                ON
                    T1.TEAM_ID = T2.UNDER ) T
        ON
            (
                U.TEAM_ID = T.T5
            OR  U.TEAM_ID = T4
            OR  U.TEAM_ID = T3
            OR  U.TEAM_ID = T2
            OR  U.TEAM_ID = T1)
            AND T.USER_ID = CUR.USER_ID
        JOIN
            ORIG_IAS.USER_ROLE UR
        ON
            (
                UR.USER_NAME = T.USER_ID )
        JOIN
            ORIG_IAS.ROLE R
        ON
            R.ROLE_ID = UR.ROLE_ID
        JOIN
            (
                SELECT
                    SUBSTR (CSV, INSTR (CSV, ',', 1, LEV) + 1, INSTR (CSV, ',', 1, LEV + 1) - INSTR (CSV,
                    ',', 1, LEV) - 1) JOB_STATE,
                    PARAM_CODE
                FROM
                    (
                        SELECT
                            ',' || PARAM_VALUE || ',' CSV,
                            PARAM_CODE
                        FROM
                            GENERAL_PARAM
                        WHERE
                            PARAM_CODE LIKE 'WIP_JOBSTATE_%' ),
                    (
                        SELECT
                            LEVEL LEV
                        FROM
                            DUAL CONNECT BY LEVEL <= 100)
                WHERE
                    LEV <= LENGTH (CSV) - LENGTH (REPLACE (CSV, ',')) - 1) T
        ON
            T.JOB_STATE = OG.JOB_STATE
        AND T.PARAM_CODE = 'WIP_JOBSTATE_' || R.ROLE_NAME
        JOIN
            MS_USER_TEAM U
        ON
            U.USER_ID = UR.USER_NAME
        WHERE
            EXISTS
            (
                SELECT
                    *
                FROM
                    LSW_TASK LT
                WHERE
                    LT.BPD_INSTANCE_ID = OG.INSTANT_ID
                AND LT.TASK_ID =
                    (
                        SELECT
                            MAX(TASK_ID)
                        FROM
                            LSW_TASK X
                        WHERE
                            X.BPD_INSTANCE_ID = LT.BPD_INSTANCE_ID)
                AND LT.STATUS = 12
                AND OG.PRIORITY IN (1,2) )
        AND NOT EXISTS
            (
                SELECT DISTINCT
                    CASE
                        WHEN OG1.PRIORITY IN (1,2)
                        THEN OG1.APPLICATION_GROUP_ID
                        ELSE NULL
                    END INCOMING_APP
                FROM
                    ORIG_APPLICATION_GROUP OG1
                    --                                        JOIN LSW_TASK LT ON LT.BPD_INSTANCE_ID =
                    -- OG1.INSTANT_ID
                    --                                        JOIN LSW_USR_XREF LU ON LU.USER_ID =
                    -- LT.USER_ID
                JOIN
                    MS_USER_TEAM U
                ON
                    U.USER_ID = CUR.USER_ID
                JOIN
                    (
                        SELECT
                            T5.TEAM_ID T5 ,
                            MT5.POSITION_ID,
                            MT5.USER_ID ,
                            T4.TEAM_ID T4,
                            T3.TEAM_ID T3 ,
                            T2.TEAM_ID T2,
                            T1.TEAM_ID T1
                        FROM
                            MS_TEAM T5
                        JOIN
                            MS_USER_TEAM MT5
                        ON
                            T5.TEAM_ID = MT5.TEAM_ID
                        LEFT JOIN
                            MS_TEAM T4
                        ON
                            T4.TEAM_ID = T5.UNDER
                        LEFT JOIN
                            MS_TEAM T3
                        ON
                            T3.TEAM_ID = T4.UNDER
                        LEFT JOIN
                            MS_TEAM T2
                        ON
                            T2.TEAM_ID = T3.UNDER
                        LEFT JOIN
                            MS_TEAM T1
                        ON
                            T1.TEAM_ID = T2.UNDER ) T
                ON
                    (
                        U.TEAM_ID = T.T5
                    OR  U.TEAM_ID = T4
                    OR  U.TEAM_ID = T3
                    OR  U.TEAM_ID = T2
                    OR  U.TEAM_ID = T1)
                    AND T.USER_ID = CUR.USER_ID
                JOIN
                    ORIG_IAS.USER_ROLE UR
                ON
                    (
                        UR.USER_NAME = T.USER_ID )
                JOIN
                    ORIG_IAS.ROLE R
                ON
                    R.ROLE_ID = UR.ROLE_ID
                LEFT JOIN
                    REPORT_PARAM RP_BS
                ON
                    RP_BS.PARAM_TYPE = 'BASELINE_APP'
                AND RP_BS.PARAM_CODE = R.ROLE_NAME
                LEFT JOIN
                    REPORT_PARAM RP_OT
                ON
                    RP_OT.PARAM_TYPE = 'OT_HOUR_WEEKDAY'
                AND RP_OT.PARAM_CODE = R.ROLE_NAME
                LEFT JOIN
                    REPORT_PARAM RP_OTH
                ON
                    RP_OTH.PARAM_TYPE = 'OT_HOUR_HOLIDAY'
                AND RP_OTH.PARAM_CODE = R.ROLE_NAME
                JOIN
                    (
                        SELECT
                            JOB_STATE.JOB_STATE,
                            INCOMING.INCOMING_CODE,
                            INCOMING.PARAM_CODE
                        FROM
                            (
                                SELECT
                                    SUBSTR (CSV, INSTR (CSV, ',', 1, LEV) + 1, INSTR (CSV, ',', 1, LEV + 1)
                                    - INSTR (CSV, ',', 1, LEV) - 1) JOB_STATE,
                                    PARAM_CODE
                                FROM
                                    (
                                        SELECT
                                            ',' || PARAM_VALUE || ',' CSV,
                                            PARAM_CODE
                                        FROM
                                            GENERAL_PARAM ),
                                    (
                                        SELECT
                                            LEVEL LEV
                                        FROM
                                            DUAL CONNECT BY LEVEL <= 100)
                                WHERE
                                    LEV <= LENGTH (CSV) - LENGTH (REPLACE (CSV, ',')) - 1) JOB_STATE
                        JOIN
                            (
                                SELECT
                                    SUBSTR (CSV, INSTR (CSV, ',', 1, LEV) + 1, INSTR (CSV, ',', 1, LEV + 1)
                                    - INSTR (CSV, ',', 1, LEV) - 1) INCOMING_CODE,
                                    PARAM_CODE
                                FROM
                                    (
                                        SELECT
                                            ',' || PARAM_VALUE2 || ',' CSV,
                                            PARAM_CODE
                                        FROM
                                            GENERAL_PARAM ),
                                    (
                                        SELECT
                                            LEVEL LEV
                                        FROM
                                            DUAL CONNECT BY LEVEL <= 100)
                                WHERE
                                    LEV <= LENGTH (CSV) - LENGTH (REPLACE (CSV, ',')) - 1) INCOMING
                        ON
                            INCOMING.INCOMING_CODE = JOB_STATE.PARAM_CODE
                        WHERE
                            INCOMING.PARAM_CODE LIKE 'WIP_JOBSTATE%') T
                ON
                    ( (
                            T.JOB_STATE = OG1.JOB_STATE
                        AND T.PARAM_CODE = 'WIP_JOBSTATE_' || R.ROLE_NAME )
                    OR  (
                            R.ROLE_NAME IN ('DE1_1',
                                            'IA',
                                            'DE1_2')
                        AND EXISTS
                            (
                                SELECT
                                    1
                                FROM
                                    (
                                        SELECT
                                            SUBSTR (CSV, INSTR (CSV, ',', 1, LEV) + 1, INSTR (CSV, ',', 1,
                                            LEV + 1) - INSTR (CSV, ',', 1, LEV) - 1) JOB_STATE,
                                            PARAM_CODE
                                        FROM
                                            (
                                                SELECT
                                                    ',' || PARAM_VALUE || ',' CSV,
                                                    PARAM_CODE
                                                FROM
                                                    GENERAL_PARAM
                                                WHERE
                                                    PARAM_CODE LIKE 'WIP_JOBSTATE_FU' ),
                                            (
                                                SELECT
                                                    LEVEL LEV
                                                FROM
                                                    DUAL CONNECT BY LEVEL <= 100)
                                        WHERE
                                            LEV <= LENGTH (CSV) - LENGTH (REPLACE (CSV, ',')) - 1) T
                                WHERE
                                    T.JOB_STATE = OG1.JOB_STATE
                                AND OG1.PREV_JOB_STATE IN
                                    (
                                        SELECT
                                            SUBSTR (CSV, INSTR (CSV, ',', 1, LEV) + 1, INSTR (CSV, ',', 1,
                                            LEV + 1) - INSTR (CSV, ',', 1, LEV) - 1) JOB_STATE
                                        FROM
                                            (
                                                SELECT
                                                    ',' || PARAM_VALUE || ',' CSV,
                                                    PARAM_CODE
                                                FROM
                                                    GENERAL_PARAM
                                                WHERE
                                                    PARAM_CODE IN ( 'WIP_JOBSTATE_IA' ,
                                                                   'WIP_JOBSTATE_DE1_1',
                                                                   'WIP_JOBSTATE_DE1_2')),
                                            (
                                                SELECT
                                                    LEVEL LEV
                                                FROM
                                                    DUAL CONNECT BY LEVEL <= 100)
                                        WHERE
                                            LEV <= LENGTH (CSV) - LENGTH (REPLACE (CSV, ',')) - 1))))
                WHERE
                    ((
                            R.ROLE_NAME != 'VT'
                        AND R.ROLE_NAME !='CA')
                    OR  (
                            R.ROLE_NAME != 'VT'
                        AND EXISTS
                            (
                                SELECT
                                    1
                                FROM
                                    ORIG_APPLICATION AP
                                WHERE
                                    AP.APPLICATION_GROUP_ID = OG1.APPLICATION_GROUP_ID
                                AND (
                                        LIFE_CYCLE > 1
                                    OR  LAST_DECISION = 'RE') )) )
                AND OG.APPLICATION_GROUP_ID = OG1.APPLICATION_GROUP_ID ) ;*/
        IF   NVL(V_OT_HOUR,0) * NVL(V_BASELINE,0) = 0 THEN
                V_OT_CELL_TODAY  := 0;
        ELSE
               V_OT_CELL_TODAY  :=( (V_INCOMING_APP_URGENT + V_ON_HAND_URGENT) * V_PERCENT_BUFFER) / (V_OT_HOUR * V_BASELINE) ;
        END IF;

        IF    NVL(V_OT_HOUR_HOLIDAY,0) * NVL(V_BASELINE,0) = 0 THEN
                V_OT_CELL_D_1 := 0;
                V_OT_CELL_D_2 := 0;
        ELSE
                V_OT_CELL_D_1 :=  ((V_URGENT_D_1) * V_PERCENT_BUFFER)/(V_OT_HOUR_HOLIDAY * V_BASELINE) ;
                V_OT_CELL_D_2 :=  ((V_URGENT_D_2) * V_PERCENT_BUFFER)/(V_OT_HOUR_HOLIDAY * V_BASELINE) ;
        END IF;

--        SELECT
--            CASE WHEN (NVL(V_OT_HOUR * V_BASELINE,0)) <> 0 THEN
--                SUM(( (V_INCOMING_APP_URGENT + V_ON_HAND_URGENT) * V_PERCENT_BUFFER) / (V_OT_HOUR * V_BASELINE) )
--             ELSE 0 END
--            ,CASE WHEN (NVL(V_OT_HOUR_HOLIDAY * V_BASELINE,0)) <> 0 THEN
--                SUM(((V_URGENT_D_1) * V_PERCENT_BUFFER)/(V_OT_HOUR_HOLIDAY * V_BASELINE))
--             ELSE 0 END
--            ,CASE WHEN (NVL(V_OT_HOUR_HOLIDAY * V_BASELINE,0)) <> 0 THEN
--                 SUM(((V_URGENT_D_2) * V_PERCENT_BUFFER)/(V_OT_HOUR_HOLIDAY * V_BASELINE))
--            ELSE 0 END
--        INTO
--            V_OT_CELL_TODAY,
--            V_OT_CELL_D_1,
--            V_OT_CELL_D_2
--        FROM
--            DUAL;

        --URGENT APP(D+1) --
        SELECT
            DHB_SUMMARY_DATA_PK.NEXTVAL
        INTO
            V_SUMMARY_ID
        FROM
            DUAL ;
        V_DHB_TYPE := '05';
        V_POSITION_LEVEL := CUR.POSITION_ID;
        V_DBH_OWNER := CUR.USER_ID;
        V_DBH_GROUP := 1;
        V_DBH_SEQ :=1;

        INSERT
        INTO
            DHB_SUMMARY_DATA
            (
                SUMMARY_ID,
                POSITION_LEVEL ,
                DHB_TYPE ,
                DHB_OWNER ,
                DHB_GROUP,
                DHP_SEQ,
                VALUE1 ,
                DESCRIPTION1,
                VALUE2,
                DESCRIPTION2 ,
                VALUE3,
                DESCRIPTION3 ,
                VALUE4 ,
                DESCRIPTION4,
                CREATE_DATE,
                STATUS
            )
            VALUES
            (
                V_SUMMARY_ID,
                V_POSITION_LEVEL,
                V_DHB_TYPE,
                V_DBH_OWNER,
                V_DBH_GROUP,
                V_DBH_SEQ,
                CEIL(NVL(V_URGENT_D_1,0)),
                'Urgent app(D+1)',
                NULL,
                NULL,
                NULL,
                NULL,
                NULL,
                NULL,
                V_APPLICATION_DATE,
                'W'
            );
        COMMIT;

        --URGENT APP(D+2) --
        SELECT
            DHB_SUMMARY_DATA_PK.NEXTVAL
        INTO
            V_SUMMARY_ID
        FROM
            DUAL ;
        V_DHB_TYPE := '05';
        V_POSITION_LEVEL := CUR.POSITION_ID;
        V_DBH_OWNER := CUR.USER_ID;
        V_DBH_GROUP := 1;
        V_DBH_SEQ :=2;

        INSERT
        INTO
            DHB_SUMMARY_DATA
            (
                SUMMARY_ID,
                POSITION_LEVEL ,
                DHB_TYPE ,
                DHB_OWNER ,
                DHB_GROUP,
                DHP_SEQ,
                VALUE1 ,
                DESCRIPTION1,
                VALUE2,
                DESCRIPTION2 ,
                VALUE3,
                DESCRIPTION3 ,
                VALUE4 ,
                DESCRIPTION4,
                CREATE_DATE,
                STATUS
            )
            VALUES
            (
                V_SUMMARY_ID,
                V_POSITION_LEVEL,
                V_DHB_TYPE,
                V_DBH_OWNER,
                V_DBH_GROUP,
                V_DBH_SEQ,
                CEIL(NVL(V_URGENT_D_2,0)),
                'Urgent app(D+2)',
                NULL,
                NULL,
                NULL,
                NULL,
                NULL,
                NULL,
                V_APPLICATION_DATE,
                'W'
            );
        COMMIT;

        -- OT CALL TODAY --

        SELECT
            DHB_SUMMARY_DATA_PK.NEXTVAL
        INTO
            V_SUMMARY_ID
        FROM
            DUAL ;
        V_DHB_TYPE := '06';
        V_POSITION_LEVEL := CUR.POSITION_ID;
        V_DBH_OWNER := CUR.USER_ID;
        V_DBH_GROUP := 1;
        V_DBH_SEQ :=1;

        INSERT
        INTO
            DHB_SUMMARY_DATA
            (
                SUMMARY_ID,
                POSITION_LEVEL ,
                DHB_TYPE ,
                DHB_OWNER ,
                DHB_GROUP,
                DHP_SEQ,
                VALUE1 ,
                DESCRIPTION1,
                VALUE2,
                DESCRIPTION2 ,
                VALUE3,
                DESCRIPTION3 ,
                VALUE4 ,
                DESCRIPTION4,
                CREATE_DATE,
                STATUS
            )
            VALUES
            (
                V_SUMMARY_ID,
                V_POSITION_LEVEL,
                V_DHB_TYPE,
                V_DBH_OWNER,
                V_DBH_GROUP,
                V_DBH_SEQ,
                CEIL(NVL(V_OT_CELL_TODAY,0)),
                'OT Call Today',
                NULL,
                NULL,
                NULL,
                NULL,
                NULL,
                NULL,
                V_APPLICATION_DATE,
                'W'
            );
        COMMIT;
        --OT CALL D + 1 --
        SELECT
            DHB_SUMMARY_DATA_PK.NEXTVAL
        INTO
            V_SUMMARY_ID
        FROM
            DUAL ;
        V_DHB_TYPE := '06';
        V_POSITION_LEVEL := CUR.POSITION_ID;
        V_DBH_OWNER := CUR.USER_ID;
        V_DBH_GROUP := 1;
        V_DBH_SEQ :=2;

        INSERT
        INTO
            DHB_SUMMARY_DATA
            (
                SUMMARY_ID,
                POSITION_LEVEL ,
                DHB_TYPE ,
                DHB_OWNER ,
                DHB_GROUP,
                DHP_SEQ,
                VALUE1 ,
                DESCRIPTION1,
                VALUE2,
                DESCRIPTION2 ,
                VALUE3,
                DESCRIPTION3 ,
                VALUE4 ,
                DESCRIPTION4,
                CREATE_DATE,
                STATUS
            )
            VALUES
            (
                V_SUMMARY_ID,
                V_POSITION_LEVEL,
                V_DHB_TYPE,
                V_DBH_OWNER,
                V_DBH_GROUP,
                V_DBH_SEQ,
                CEIL(NVL(V_OT_CELL_D_1,0)),
                'OT Call D+1',
                NULL,
                NULL,
                NULL,
                NULL,
                NULL,
                NULL,
                V_APPLICATION_DATE,
                'W'
            );
        COMMIT;

        --OT CALL D + 2 --
        SELECT
            DHB_SUMMARY_DATA_PK.NEXTVAL
        INTO
            V_SUMMARY_ID
        FROM
            DUAL ;
        V_DHB_TYPE := '06';
        V_POSITION_LEVEL := CUR.POSITION_ID;
        V_DBH_OWNER := CUR.USER_ID;
        V_DBH_GROUP := 1;
        V_DBH_SEQ :=3;

        INSERT
        INTO
            DHB_SUMMARY_DATA
            (
                SUMMARY_ID,
                POSITION_LEVEL ,
                DHB_TYPE ,
                DHB_OWNER ,
                DHB_GROUP,
                DHP_SEQ,
                VALUE1 ,
                DESCRIPTION1,
                VALUE2,
                DESCRIPTION2 ,
                VALUE3,
                DESCRIPTION3 ,
                VALUE4 ,
                DESCRIPTION4,
                CREATE_DATE,
                STATUS
            )
            VALUES
            (
                V_SUMMARY_ID,
                V_POSITION_LEVEL,
                V_DHB_TYPE,
                V_DBH_OWNER,
                V_DBH_GROUP,
                V_DBH_SEQ,
                CEIL(NVL(V_OT_CELL_D_2,0)),
                'OT Call D+2',
                NULL,
                NULL,
                NULL,
                NULL,
                NULL,
                NULL,
                V_APPLICATION_DATE,
                'W'
            );
        COMMIT;
    END LOOP;
END P_GEN_DASHBOARD_05_06;

--PROCEDURE P_GEN_DASHBOARD_05_06
--AS
--    V_DHB_TYPE VARCHAR2(2) := '01';
--    V_POSITION_LEVEL VARCHAR2(3) := 'ALL';
--    V_DBH_OWNER VARCHAR2(30) := 'ALL';
--    V_DBH_GROUP NUMBER;
--    V_DBH_SEQ NUMBER;
--    V_SUMMARY_ID NUMBER;
--    V_PERCENT_BUFFER NUMBER;
--    V_OT_CELL_TODAY NUMBER;
--    V_OT_CELL_D_1 NUMBER;
--    V_OT_CELL_D_2 NUMBER;
--    V_URGENT_D_1 NUMBER;
--    V_URGENT_D_2 NUMBER;
--    V_APPLICATION_DATE DATE;
--    V_INCOMING_APP_URGENT NUMBER;
--    V_URGENT_APP NUMBER;
--    V_URGENT_APP2 NUMBER;
--    V_ON_HAND_URGENT NUMBER;
--    V_OT_HOUR NUMBER;
--    V_BASELINE NUMBER;
--    V_OT_HOUR_HOLIDAY NUMBER;
--    V_OLD_USER_ID VARCHAR2(30);
--BEGIN
--
--    -- ALL --
--    /* no need to delete data in procedure
--    DELETE FROM DHB_SUMMARY_DATA
--    WHERE DHB_TYPE IN ('05', '06');*/
--    --OT CALL TODAY--
--
--    SELECT TRUNC(APP_DATE) + (SYSDATE - TRUNC(SYSDATE))
--    INTO V_APPLICATION_DATE
--    FROM APPLICATION_DATE;
--
--    --OT CALL TODAY--
--    SELECT VALUE1/100
--    INTO V_PERCENT_BUFFER
--    FROM  REPORT_PARAM
--    WHERE  PARAM_TYPE = 'PERCENT_BUFFER'
--    AND PARAM_CODE = 'PERCENT_BUFFER';
--
--    --MANAGER--
--
--    V_OLD_USER_ID := NULL;
--
--    FOR CUR IN
--                ( SELECT DISTINCT USER_ID ,
--                    (POSITION_ID) POSITION_ID
--                FROM  MS_USER_TEAM
--                WHERE  POSITION_ID IN (1,2, 3,4))
--    LOOP
--
--        SELECT
--            COUNT(DISTINCT OG.APPLICATION_GROUP_ID
--            ) INCOMING_APP ,
--            COUNT(DISTINCT
--            CASE
--                WHEN TRUNC(OG.SLA_DUE_DATE) = TRUNC((V_APPLICATION_DATE-2) + 1)
--                THEN OG.APPLICATION_GROUP_ID
--                ELSE NULL
--            END) URGENT_APP ,
--            COUNT(DISTINCT
--            CASE
--                WHEN TRUNC(OG.SLA_DUE_DATE) = TRUNC((V_APPLICATION_DATE-2) + 2)
--                THEN OG.APPLICATION_GROUP_ID
--                ELSE NULL
--            END)               URGENT_APP_2 ,
--            MAX(RP_OT.VALUE1)  OT_HOUR ,
--            MAX(RP_BS.VALUE1)  BASELINE ,
--            MAX(RP_OTH.VALUE1) OT_HOUR_HOLIDAY
--        INTO
--            V_INCOMING_APP_URGENT,
--            V_URGENT_D_1,
--            V_URGENT_D_2,
--            V_OT_HOUR,
--            V_BASELINE,
--            V_OT_HOUR_HOLIDAY
--        FROM
--            MS_USER_TEAM U
--        JOIN
--           (SELECT DISTINCT USER_ID, POSITION_ID, PATH --MAX(PATH) PATH
--            FROM
--                        (SELECT B.USER_ID, B.POSITION_ID, A.TEAM_ID, SYS_CONNECT_BY_PATH(A.TEAM_ID,'/') PATH
--                        FROM MS_TEAM A
--                        JOIN MS_USER_TEAM B ON A.TEAM_ID = B.TEAM_ID
--                        CONNECT BY PRIOR A.TEAM_ID = A.UNDER )
--            --GROUP BY USER_ID, POSITION_ID
--            ) T
--        ON
--            INSTR(T.PATH,U.TEAM_ID) > 0
--            AND U.POSITION_ID IN (1,2, 3,4,5)
--        JOIN
--            ORIG_IAS.USER_ROLE UR
--        ON
--            ( UR.USER_NAME = T.USER_ID )
--        JOIN
--            ORIG_IAS.ROLE R
--        ON
--            R.ROLE_ID = UR.ROLE_ID
--        LEFT JOIN
--            REPORT_PARAM RP_BS
--        ON
--            RP_BS.PARAM_TYPE = 'BASELINE_APP'
--        AND RP_BS.PARAM_CODE = R.ROLE_NAME
--        LEFT JOIN
--            REPORT_PARAM RP_OT
--        ON
--            RP_OT.PARAM_TYPE = 'OT_HOUR_WEEKDAY'
--        AND RP_OT.PARAM_CODE = R.ROLE_NAME
--        LEFT JOIN
--            REPORT_PARAM RP_OTH
--        ON
--            RP_OTH.PARAM_TYPE = 'OT_HOUR_HOLIDAY'
--        AND RP_OTH.PARAM_CODE = R.ROLE_NAME
--        JOIN ORIG_APPLICATION_GROUP OG
--        ON OG.PRIORITY IN (1,2)
--        JOIN
--            (
--                SELECT
--                    JOB_STATE.JOB_STATE,
--                    INCOMING.INCOMING_CODE,
--                    INCOMING.PARAM_CODE
--                FROM DHB_JOB_STATE  JOB_STATE
--                JOIN
--                    (
--                        SELECT
--                            SUBSTR (CSV, INSTR (CSV, ',', 1, LEV) + 1, INSTR (CSV, ',', 1, LEV + 1) - INSTR
--                            (CSV, ',', 1, LEV) - 1) INCOMING_CODE,
--                            PARAM_CODE
--                        FROM
--                            (   SELECT /*+ INDEX(GENERAL_PARAM,IDX03_GENERAL_PARAM) */
--                                  ',' || PARAM_VALUE2 || ',' CSV,
--                                    PARAM_CODE
--                                FROM  GENERAL_PARAM ),
--                            (  SELECT   LEVEL LEV
--                                FROM  DUAL CONNECT BY LEVEL <= 100)
--                        WHERE
--                            LEV <= LENGTH (CSV) - LENGTH (REPLACE (CSV, ',')) - 1) INCOMING
--                ON
--                    INCOMING.INCOMING_CODE = JOB_STATE.PARAM_CODE
--                WHERE
--                    INCOMING.PARAM_CODE LIKE 'WIP_JOBSTATE%') T
--        ON
--            ( (  T.JOB_STATE = OG.JOB_STATE
--                AND T.PARAM_CODE = 'WIP_JOBSTATE_' || R.ROLE_NAME )
--            OR  ( R.ROLE_NAME IN ('DE1_1',
--                                                'IA',
--                                                'DE1_2')
--                OR  EXISTS
--                    ( SELECT 1
--                        FROM DHB_JOB_STATE T
--                        WHERE
--                            T.JOB_STATE = OG.JOB_STATE
--                            AND T.PARAM_CODE LIKE 'WIP_JOBSTATE_FU'
--                        AND OG.PREV_JOB_STATE IN
--                        (SELECT JOB_STATE
--                         FROM DHB_JOB_STATE T
--                         WHERE PARAM_CODE IN ( 'WIP_JOBSTATE_IA' ,
--                                                           'WIP_JOBSTATE_DE1_1',
--                                                           'WIP_JOBSTATE_DE1_2'))
--                            )))
--        WHERE U.USER_ID = CUR.USER_ID
--         AND
--            (     ( R.ROLE_NAME != 'VT'  AND R.ROLE_NAME !='CA')
--            OR  ( R.ROLE_NAME != 'VT'
--                AND EXISTS  (  SELECT   1
--                                        FROM  ORIG_APPLICATION AP
--                                        WHERE  AP.APPLICATION_GROUP_ID = OG.APPLICATION_GROUP_ID
--                                        AND ( AP.LIFE_CYCLE > 1
--                                            OR  OG.LAST_DECISION = 'RE') )) ) ;
--
--        /*SELECT
--            COUNT(DISTINCT
--            CASE
--                WHEN OG.PRIORITY IN (1,2)
--                THEN OG.APPLICATION_GROUP_ID
--                ELSE NULL
--            END) INCOMING_APP ,
--            COUNT(DISTINCT
--            CASE
--                WHEN OG.PRIORITY IN (1,2)
--                AND TRUNC(OG.SLA_DUE_DATE) = TRUNC(V_APPLICATION_DATE + 1)
--                THEN OG.APPLICATION_GROUP_ID
--                ELSE NULL
--            END) URGENT_APP ,
--            COUNT(DISTINCT
--            CASE
--                WHEN OG.PRIORITY IN (1,2)
--                AND TRUNC(OG.SLA_DUE_DATE) = TRUNC(V_APPLICATION_DATE + 2)
--                THEN OG.APPLICATION_GROUP_ID
--                ELSE NULL
--            END)               URGENT_APP_2 ,
--            MAX(RP_OT.VALUE1)  OT_HOUR ,
--            MAX(RP_BS.VALUE1)  BASELINE ,
--            MAX(RP_OTH.VALUE1) OT_HOUR_HOLIDAY
--        INTO
--            V_INCOMING_APP_URGENT,
--            V_URGENT_D_1,
--            V_URGENT_D_2,
--            V_OT_HOUR,
--            V_BASELINE,
--            V_OT_HOUR_HOLIDAY
--        FROM
--            ORIG_APPLICATION_GROUP OG
--            --JOIN LSW_TASK LT ON LT.BPD_INSTANCE_ID = OG.INSTANT_ID
--            --JOIN LSW_USR_XREF LU ON LU.USER_ID = LT.USER_ID
--        JOIN
--            MS_USER_TEAM U
--        ON
--            U.USER_ID = CUR.USER_ID
--        JOIN
--            (
--                SELECT
--                    T5.TEAM_ID T5 ,
--                    MT5.POSITION_ID,
--                    MT5.USER_ID ,
--                    T4.TEAM_ID T4,
--                    T3.TEAM_ID T3 ,
--                    T2.TEAM_ID T2,
--                    T1.TEAM_ID T1
--                FROM
--                    MS_TEAM T5
--                JOIN
--                    MS_USER_TEAM MT5
--                ON
--                    T5.TEAM_ID = MT5.TEAM_ID
--                LEFT JOIN
--                    MS_TEAM T4
--                ON
--                    T4.TEAM_ID = T5.UNDER
--                LEFT JOIN
--                    MS_TEAM T3
--                ON
--                    T3.TEAM_ID = T4.UNDER
--                LEFT JOIN
--                    MS_TEAM T2
--                ON
--                    T2.TEAM_ID = T3.UNDER
--                LEFT JOIN
--                    MS_TEAM T1
--                ON
--                    T1.TEAM_ID = T2.UNDER ) T
--        ON
--            (
--                U.TEAM_ID = T.T5
--            OR  U.TEAM_ID = T4
--            OR  U.TEAM_ID = T3
--            OR  U.TEAM_ID = T2
--            OR  U.TEAM_ID = T1)
--            AND U.USER_ID = T.USER_ID
--        JOIN
--            ORIG_IAS.USER_ROLE UR
--        ON
--            (
--                UR.USER_NAME = T.USER_ID )
--        JOIN
--            ORIG_IAS.ROLE R
--        ON
--            R.ROLE_ID = UR.ROLE_ID
--        LEFT JOIN
--            REPORT_PARAM RP_BS
--        ON
--            RP_BS.PARAM_TYPE = 'BASELINE_APP'
--        AND RP_BS.PARAM_CODE = R.ROLE_NAME
--        LEFT JOIN
--            REPORT_PARAM RP_OT
--        ON
--            RP_OT.PARAM_TYPE = 'OT_HOUR_WEEKDAY'
--        AND RP_OT.PARAM_CODE = R.ROLE_NAME
--        LEFT JOIN
--            REPORT_PARAM RP_OTH
--        ON
--            RP_OTH.PARAM_TYPE = 'OT_HOUR_HOLIDAY'
--        AND RP_OTH.PARAM_CODE = R.ROLE_NAME
--        JOIN
--            (
--                SELECT
--                    JOB_STATE.JOB_STATE,
--                    INCOMING.INCOMING_CODE,
--                    INCOMING.PARAM_CODE
--                FROM
--                    (
--                        SELECT
--                            SUBSTR (CSV, INSTR (CSV, ',', 1, LEV) + 1, INSTR (CSV, ',', 1, LEV + 1) - INSTR
--                            (CSV, ',', 1, LEV) - 1) JOB_STATE,
--                            PARAM_CODE
--                        FROM
--                            (
--                                SELECT
--                                    ',' || PARAM_VALUE || ',' CSV,
--                                    PARAM_CODE
--                                FROM
--                                    GENERAL_PARAM ),
--                            (
--                                SELECT
--                                    LEVEL LEV
--                                FROM
--                                    DUAL CONNECT BY LEVEL <= 100)
--                        WHERE
--                            LEV <= LENGTH (CSV) - LENGTH (REPLACE (CSV, ',')) - 1) JOB_STATE
--                JOIN
--                    (
--                        SELECT
--                            SUBSTR (CSV, INSTR (CSV, ',', 1, LEV) + 1, INSTR (CSV, ',', 1, LEV + 1) - INSTR
--                            (CSV, ',', 1, LEV) - 1) INCOMING_CODE,
--                            PARAM_CODE
--                        FROM
--                            (
--                                SELECT
--                                    ',' || PARAM_VALUE2 || ',' CSV,
--                                    PARAM_CODE
--                                FROM
--                                    GENERAL_PARAM ),
--                            (
--                                SELECT
--                                    LEVEL LEV
--                                FROM
--                                    DUAL CONNECT BY LEVEL <= 100)
--                        WHERE
--                            LEV <= LENGTH (CSV) - LENGTH (REPLACE (CSV, ',')) - 1) INCOMING
--                ON
--                    INCOMING.INCOMING_CODE = JOB_STATE.PARAM_CODE
--                WHERE
--                    INCOMING.PARAM_CODE LIKE 'WIP_JOBSTATE%') T
--        ON
--            ( (
--                    T.JOB_STATE = OG.JOB_STATE
--                AND T.PARAM_CODE = 'WIP_JOBSTATE_' || R.ROLE_NAME )
--            OR  (
--                    R.ROLE_NAME IN ('DE1_1',
--                                    'IA',
--                                    'DE1_2')
--                OR  EXISTS
--                    (
--                        SELECT
--                            1
--                        FROM
--                            (
--                                SELECT
--                                    SUBSTR (CSV, INSTR (CSV, ',', 1, LEV) + 1, INSTR (CSV, ',', 1, LEV + 1)
--                                    - INSTR (CSV, ',', 1, LEV) - 1) JOB_STATE,
--                                    PARAM_CODE
--                                FROM
--                                    (
--                                        SELECT
--                                            ',' || PARAM_VALUE || ',' CSV,
--                                            PARAM_CODE
--                                        FROM
--                                            GENERAL_PARAM
--                                        WHERE
--                                            PARAM_CODE LIKE 'WIP_JOBSTATE_FU' ),
--                                    (
--                                        SELECT
--                                            LEVEL LEV
--                                        FROM
--                                            DUAL CONNECT BY LEVEL <= 100)
--                                WHERE
--                                    LEV <= LENGTH (CSV) - LENGTH (REPLACE (CSV, ',')) - 1) T
--                        WHERE
--                            T.JOB_STATE = OG.JOB_STATE
--                        AND OG.PREV_JOB_STATE IN
--                            (
--                                SELECT
--                                    SUBSTR (CSV, INSTR (CSV, ',', 1, LEV) + 1, INSTR (CSV, ',', 1, LEV + 1)
--                                    - INSTR (CSV, ',', 1, LEV) - 1) JOB_STATE
--                                FROM
--                                    (
--                                        SELECT
--                                            ',' || PARAM_VALUE || ',' CSV,
--                                            PARAM_CODE
--                                        FROM
--                                            GENERAL_PARAM
--                                        WHERE
--                                            PARAM_CODE IN ( 'WIP_JOBSTATE_IA' ,
--                                                           'WIP_JOBSTATE_DE1_1',
--                                                           'WIP_JOBSTATE_DE1_2')),
--                                    (
--                                        SELECT
--                                            LEVEL LEV
--                                        FROM
--                                            DUAL CONNECT BY LEVEL <= 100)
--                                WHERE
--                                    LEV <= LENGTH (CSV) - LENGTH (REPLACE (CSV, ',')) - 1))))
--        WHERE
--            ((
--                    R.ROLE_NAME != 'VT'
--                AND R.ROLE_NAME !='CA')
--            OR  (
--                    R.ROLE_NAME != 'VT'
--                AND EXISTS
--                    (
--                        SELECT
--                            1
--                        FROM
--                            ORIG_APPLICATION AP
--                        WHERE
--                            AP.APPLICATION_GROUP_ID = OG.APPLICATION_GROUP_ID
--                        AND (
--                                LIFE_CYCLE > 1
--                            OR  LAST_DECISION = 'RE') )) ) ; */
--
--        /*select  count(distinct case when og.priority in (1,2) then og.INSTANT_ID else null end)
--        into  v_on_hand_urgent
--        from orig_application_group og
--        join LSW_TASK lt on LT.BPD_INSTANCE_ID = OG.INSTANT_ID
--        join LSW_USR_XREF lu on lu.USER_ID = LT.USER_ID
--        where LT.STATUS = '12'
--        and  exists(
--        select * from ms_user_team mu join
--        (
--        select t5.under,t5.team_id t5 ,mt5.position_id,mt5.user_id ,t4.team_id t4,t3.team_id t3 ,t2.team_id
--        t2,t1.team_id t1
--        from ms_user_team mt5
--        join ms_team t5 on t5.team_id = mt5.team_id
--        left join ms_team t4 on t4.under = t5.team_id
--        left join ms_team t3 on t3.under = t4.team_id
--        left join ms_team t2 on t2.under = t3.team_id
--        left join ms_team t1 on t1.under = t2.team_id
--        where  mt5.user_id = cur.user_id
--        ) t on (mu.team_id  = t.t5 or mu.team_id = t4 or mu.team_id = t3 or mu.team_id = t2 or mu.team_id =
--        t1)
--        where mu.user_id = lu.user_name ); */
--
--SELECT
--        COUNT(DISTINCT APPLICATION_GROUP_ID)
--        INTO
--            V_ON_HAND_URGENT
--        FROM
--        (
--        SELECT DISTINCT OG.APPLICATION_GROUP_ID
--        FROM
--            MS_USER_TEAM U
--        JOIN
--           (SELECT USER_ID, POSITION_ID, MIN(PATH) PATH
--            FROM
--            (SELECT B.USER_ID, B.POSITION_ID, A.TEAM_ID, SYS_CONNECT_BY_PATH(A.TEAM_ID,'/') PATH
--            FROM MS_TEAM A
--            JOIN MS_USER_TEAM B ON A.TEAM_ID = B.TEAM_ID
--            CONNECT BY PRIOR A.TEAM_ID = A.UNDER)
--            GROUP BY USER_ID, POSITION_ID) T
--        ON
--            INSTR(T.PATH,U.TEAM_ID) > 0
--            AND U.USER_ID = CUR.USER_ID
--        JOIN
--            ORIG_IAS.USER_ROLE UR
--        ON
--            (
--                UR.USER_NAME = T.USER_ID )
--        JOIN
--            ORIG_IAS.ROLE R
--        ON
--            R.ROLE_ID = UR.ROLE_ID
--
--        JOIN DHB_JOB_STATE JST
--        ON
--        --T.PARAM_CODE LIKE 'WIP_JOBSTATE_%'
--        --AND
--        JST.PARAM_CODE = 'WIP_JOBSTATE_' || R.ROLE_NAME
--        JOIN ORIG_APPLICATION_GROUP OG
--        ON
--            JST.JOB_STATE = OG.JOB_STATE
--            AND OG.PRIORITY IN (1,2)
--        JOIN  LSW_TASK LT
--                ON
--                    LT.BPD_INSTANCE_ID = OG.INSTANT_ID
--                AND LT.STATUS = 12
--                AND LT.TASK_ID =
--                    (
--                        SELECT
--                            MAX(TASK_ID)
--                        FROM
--                            LSW_TASK X
--                        WHERE
--                            X.BPD_INSTANCE_ID = LT.BPD_INSTANCE_ID)
--        WHERE
--        --U.POSITION_ID IN (1,2,3,4)
--
--        --AND
--        U.USER_ID = CUR.USER_ID
--        --JOIN
--        --    MS_USER_TEAM U
--       -- ON
--        --    U.USER_ID = UR.USER_NAME
--
--       -- GROUP BY U.USER_ID;
--        MINUS
--            (
--                SELECT DISTINCT OG1.APPLICATION_GROUP_ID
--
--                FROM
--
--
--                    MS_USER_TEAM U
--
--                JOIN
--                    (SELECT USER_ID, POSITION_ID, MIN(PATH) PATH
--                    FROM
--                    (SELECT B.USER_ID, B.POSITION_ID, A.TEAM_ID, SYS_CONNECT_BY_PATH(A.TEAM_ID,'/') PATH
--                    FROM MS_TEAM A
--                    JOIN MS_USER_TEAM B ON A.TEAM_ID = B.TEAM_ID
--                    CONNECT BY PRIOR A.TEAM_ID = A.UNDER)
--                    GROUP BY USER_ID, POSITION_ID) T
--                ON
--                    INSTR(T.PATH,U.TEAM_ID) > 0
--                JOIN
--                    ORIG_IAS.USER_ROLE UR
--                ON
--                    (
--                        UR.USER_NAME = T.USER_ID )
--                JOIN
--                    ORIG_IAS.ROLE R
--                ON
--                    R.ROLE_ID = UR.ROLE_ID
--                LEFT JOIN
--                    REPORT_PARAM RP_BS
--                ON
--                    RP_BS.PARAM_TYPE = 'BASELINE_APP'
--                AND RP_BS.PARAM_CODE = R.ROLE_NAME
--                LEFT JOIN
--                    REPORT_PARAM RP_OT
--                ON
--                    RP_OT.PARAM_TYPE = 'OT_HOUR_WEEKDAY'
--                AND RP_OT.PARAM_CODE = R.ROLE_NAME
--                LEFT JOIN
--                    REPORT_PARAM RP_OTH
--                ON
--                    RP_OTH.PARAM_TYPE = 'OT_HOUR_HOLIDAY'
--                AND RP_OTH.PARAM_CODE = R.ROLE_NAME
--                JOIN ORIG_APPLICATION_GROUP OG1 ON OG1.PRIORITY IN (1,2)
--                AND
--                    ((
--                            R.ROLE_NAME != 'VT'
--                        AND R.ROLE_NAME !='CA')
--                    OR  (
--                            R.ROLE_NAME != 'VT'
--                        AND EXISTS
--                            (
--                                SELECT
--                                    1
--                                FROM
--                                    ORIG_APPLICATION AP
--                                WHERE
--                                    AP.APPLICATION_GROUP_ID = OG1.APPLICATION_GROUP_ID
--                                AND (
--                                        LIFE_CYCLE > 1
--                                    OR  LAST_DECISION = 'RE') )) )
--                JOIN
--                    (
--                        SELECT
--                            JOB_STATE.JOB_STATE,
--                            INCOMING.INCOMING_CODE,
--                            INCOMING.PARAM_CODE
--                        FROM DHB_JOB_STATE
--                             JOB_STATE
--                        JOIN
--                            (
--                                SELECT
--                                    SUBSTR (CSV, INSTR (CSV, ',', 1, LEV) + 1, INSTR (CSV, ',', 1, LEV + 1)
--                                    - INSTR (CSV, ',', 1, LEV) - 1) INCOMING_CODE,
--                                    PARAM_CODE
--                                FROM
--                                    (
--                                        SELECT
--                                            ',' || PARAM_VALUE2 || ',' CSV,
--                                            PARAM_CODE
--                                        FROM
--                                            GENERAL_PARAM ),
--                                    (
--                                        SELECT
--                                            LEVEL LEV
--                                        FROM
--                                            DUAL CONNECT BY LEVEL <= 100)
--                                WHERE
--                                    LEV <= LENGTH (CSV) - LENGTH (REPLACE (CSV, ',')) - 1) INCOMING
--                        ON
--                            INCOMING.INCOMING_CODE = JOB_STATE.PARAM_CODE
--                        WHERE
--                            INCOMING.PARAM_CODE LIKE 'WIP_JOBSTATE%') T
--                ON
--                    ( (
--                            T.JOB_STATE = OG1.JOB_STATE
--                        AND T.PARAM_CODE = 'WIP_JOBSTATE_' || R.ROLE_NAME )
--                    OR  (
--                            R.ROLE_NAME IN ('DE1_1',
--                                            'IA',
--                                            'DE1_2')
--                        AND EXISTS
--                            (
--                                SELECT
--                                    1
--                                FROM DHB_JOB_STATE T
--                                WHERE
--                                    T.PARAM_CODE LIKE 'WIP_JOBSTATE_FU' AND
--                                    T.JOB_STATE = OG1.JOB_STATE
--                                AND OG1.PREV_JOB_STATE IN
--                                    ( SELECT JOB_STATE FROM DHB_JOB_STATE
--                                      WHERE PARAM_CODE IN ( 'WIP_JOBSTATE_IA' ,
--                                                                   'WIP_JOBSTATE_DE1_1',
--                                                                   'WIP_JOBSTATE_DE1_2')
--                                        ))))
--                WHERE
--               U.USER_ID = CUR.USER_ID
--
--                ));
--
--        /*SELECT
--            COUNT(DISTINCT OG.APPLICATION_GROUP_ID)
--        INTO
--            V_ON_HAND_URGENT
--        FROM
--            ORIG_APPLICATION_GROUP OG
--        JOIN
--            MS_USER_TEAM U
--        ON
--            U.USER_ID = CUR.USER_ID
--        JOIN
--            (
--                SELECT
--                    T5.TEAM_ID T5 ,
--                    MT5.POSITION_ID,
--                    MT5.USER_ID ,
--                    T4.TEAM_ID T4,
--                    T3.TEAM_ID T3 ,
--                    T2.TEAM_ID T2,
--                    T1.TEAM_ID T1
--                FROM
--                    MS_TEAM T5
--                JOIN
--                    MS_USER_TEAM MT5
--                ON
--                    T5.TEAM_ID = MT5.TEAM_ID
--                LEFT JOIN
--                    MS_TEAM T4
--                ON
--                    T4.TEAM_ID = T5.UNDER
--                LEFT JOIN
--                    MS_TEAM T3
--                ON
--                    T3.TEAM_ID = T4.UNDER
--                LEFT JOIN
--                    MS_TEAM T2
--                ON
--                    T2.TEAM_ID = T3.UNDER
--                LEFT JOIN
--                    MS_TEAM T1
--                ON
--                    T1.TEAM_ID = T2.UNDER ) T
--        ON
--            (
--                U.TEAM_ID = T.T5
--            OR  U.TEAM_ID = T4
--            OR  U.TEAM_ID = T3
--            OR  U.TEAM_ID = T2
--            OR  U.TEAM_ID = T1)
--            AND T.USER_ID = CUR.USER_ID
--        JOIN
--            ORIG_IAS.USER_ROLE UR
--        ON
--            (
--                UR.USER_NAME = T.USER_ID )
--        JOIN
--            ORIG_IAS.ROLE R
--        ON
--            R.ROLE_ID = UR.ROLE_ID
--        JOIN
--            (
--                SELECT
--                    SUBSTR (CSV, INSTR (CSV, ',', 1, LEV) + 1, INSTR (CSV, ',', 1, LEV + 1) - INSTR (CSV,
--                    ',', 1, LEV) - 1) JOB_STATE,
--                    PARAM_CODE
--                FROM
--                    (
--                        SELECT
--                            ',' || PARAM_VALUE || ',' CSV,
--                            PARAM_CODE
--                        FROM
--                            GENERAL_PARAM
--                        WHERE
--                            PARAM_CODE LIKE 'WIP_JOBSTATE_%' ),
--                    (
--                        SELECT
--                            LEVEL LEV
--                        FROM
--                            DUAL CONNECT BY LEVEL <= 100)
--                WHERE
--                    LEV <= LENGTH (CSV) - LENGTH (REPLACE (CSV, ',')) - 1) T
--        ON
--            T.JOB_STATE = OG.JOB_STATE
--        AND T.PARAM_CODE = 'WIP_JOBSTATE_' || R.ROLE_NAME
--        JOIN
--            MS_USER_TEAM U
--        ON
--            U.USER_ID = UR.USER_NAME
--        WHERE
--            EXISTS
--            (
--                SELECT
--                    *
--                FROM
--                    LSW_TASK LT
--                WHERE
--                    LT.BPD_INSTANCE_ID = OG.INSTANT_ID
--                AND LT.TASK_ID =
--                    (
--                        SELECT
--                            MAX(TASK_ID)
--                        FROM
--                            LSW_TASK X
--                        WHERE
--                            X.BPD_INSTANCE_ID = LT.BPD_INSTANCE_ID)
--                AND LT.STATUS = 12
--                AND OG.PRIORITY IN (1,2) )
--        AND NOT EXISTS
--            (
--                SELECT DISTINCT
--                    CASE
--                        WHEN OG1.PRIORITY IN (1,2)
--                        THEN OG1.APPLICATION_GROUP_ID
--                        ELSE NULL
--                    END INCOMING_APP
--                FROM
--                    ORIG_APPLICATION_GROUP OG1
--                    --                                        JOIN LSW_TASK LT ON LT.BPD_INSTANCE_ID =
--                    -- OG1.INSTANT_ID
--                    --                                        JOIN LSW_USR_XREF LU ON LU.USER_ID =
--                    -- LT.USER_ID
--                JOIN
--                    MS_USER_TEAM U
--                ON
--                    U.USER_ID = CUR.USER_ID
--                JOIN
--                    (
--                        SELECT
--                            T5.TEAM_ID T5 ,
--                            MT5.POSITION_ID,
--                            MT5.USER_ID ,
--                            T4.TEAM_ID T4,
--                            T3.TEAM_ID T3 ,
--                            T2.TEAM_ID T2,
--                            T1.TEAM_ID T1
--                        FROM
--                            MS_TEAM T5
--                        JOIN
--                            MS_USER_TEAM MT5
--                        ON
--                            T5.TEAM_ID = MT5.TEAM_ID
--                        LEFT JOIN
--                            MS_TEAM T4
--                        ON
--                            T4.TEAM_ID = T5.UNDER
--                        LEFT JOIN
--                            MS_TEAM T3
--                        ON
--                            T3.TEAM_ID = T4.UNDER
--                        LEFT JOIN
--                            MS_TEAM T2
--                        ON
--                            T2.TEAM_ID = T3.UNDER
--                        LEFT JOIN
--                            MS_TEAM T1
--                        ON
--                            T1.TEAM_ID = T2.UNDER ) T
--                ON
--                    (
--                        U.TEAM_ID = T.T5
--                    OR  U.TEAM_ID = T4
--                    OR  U.TEAM_ID = T3
--                    OR  U.TEAM_ID = T2
--                    OR  U.TEAM_ID = T1)
--                    AND T.USER_ID = CUR.USER_ID
--                JOIN
--                    ORIG_IAS.USER_ROLE UR
--                ON
--                    (
--                        UR.USER_NAME = T.USER_ID )
--                JOIN
--                    ORIG_IAS.ROLE R
--                ON
--                    R.ROLE_ID = UR.ROLE_ID
--                LEFT JOIN
--                    REPORT_PARAM RP_BS
--                ON
--                    RP_BS.PARAM_TYPE = 'BASELINE_APP'
--                AND RP_BS.PARAM_CODE = R.ROLE_NAME
--                LEFT JOIN
--                    REPORT_PARAM RP_OT
--                ON
--                    RP_OT.PARAM_TYPE = 'OT_HOUR_WEEKDAY'
--                AND RP_OT.PARAM_CODE = R.ROLE_NAME
--                LEFT JOIN
--                    REPORT_PARAM RP_OTH
--                ON
--                    RP_OTH.PARAM_TYPE = 'OT_HOUR_HOLIDAY'
--                AND RP_OTH.PARAM_CODE = R.ROLE_NAME
--                JOIN
--                    (
--                        SELECT
--                            JOB_STATE.JOB_STATE,
--                            INCOMING.INCOMING_CODE,
--                            INCOMING.PARAM_CODE
--                        FROM
--                            (
--                                SELECT
--                                    SUBSTR (CSV, INSTR (CSV, ',', 1, LEV) + 1, INSTR (CSV, ',', 1, LEV + 1)
--                                    - INSTR (CSV, ',', 1, LEV) - 1) JOB_STATE,
--                                    PARAM_CODE
--                                FROM
--                                    (
--                                        SELECT
--                                            ',' || PARAM_VALUE || ',' CSV,
--                                            PARAM_CODE
--                                        FROM
--                                            GENERAL_PARAM ),
--                                    (
--                                        SELECT
--                                            LEVEL LEV
--                                        FROM
--                                            DUAL CONNECT BY LEVEL <= 100)
--                                WHERE
--                                    LEV <= LENGTH (CSV) - LENGTH (REPLACE (CSV, ',')) - 1) JOB_STATE
--                        JOIN
--                            (
--                                SELECT
--                                    SUBSTR (CSV, INSTR (CSV, ',', 1, LEV) + 1, INSTR (CSV, ',', 1, LEV + 1)
--                                    - INSTR (CSV, ',', 1, LEV) - 1) INCOMING_CODE,
--                                    PARAM_CODE
--                                FROM
--                                    (
--                                        SELECT
--                                            ',' || PARAM_VALUE2 || ',' CSV,
--                                            PARAM_CODE
--                                        FROM
--                                            GENERAL_PARAM ),
--                                    (
--                                        SELECT
--                                            LEVEL LEV
--                                        FROM
--                                            DUAL CONNECT BY LEVEL <= 100)
--                                WHERE
--                                    LEV <= LENGTH (CSV) - LENGTH (REPLACE (CSV, ',')) - 1) INCOMING
--                        ON
--                            INCOMING.INCOMING_CODE = JOB_STATE.PARAM_CODE
--                        WHERE
--                            INCOMING.PARAM_CODE LIKE 'WIP_JOBSTATE%') T
--                ON
--                    ( (
--                            T.JOB_STATE = OG1.JOB_STATE
--                        AND T.PARAM_CODE = 'WIP_JOBSTATE_' || R.ROLE_NAME )
--                    OR  (
--                            R.ROLE_NAME IN ('DE1_1',
--                                            'IA',
--                                            'DE1_2')
--                        AND EXISTS
--                            (
--                                SELECT
--                                    1
--                                FROM
--                                    (
--                                        SELECT
--                                            SUBSTR (CSV, INSTR (CSV, ',', 1, LEV) + 1, INSTR (CSV, ',', 1,
--                                            LEV + 1) - INSTR (CSV, ',', 1, LEV) - 1) JOB_STATE,
--                                            PARAM_CODE
--                                        FROM
--                                            (
--                                                SELECT
--                                                    ',' || PARAM_VALUE || ',' CSV,
--                                                    PARAM_CODE
--                                                FROM
--                                                    GENERAL_PARAM
--                                                WHERE
--                                                    PARAM_CODE LIKE 'WIP_JOBSTATE_FU' ),
--                                            (
--                                                SELECT
--                                                    LEVEL LEV
--                                                FROM
--                                                    DUAL CONNECT BY LEVEL <= 100)
--                                        WHERE
--                                            LEV <= LENGTH (CSV) - LENGTH (REPLACE (CSV, ',')) - 1) T
--                                WHERE
--                                    T.JOB_STATE = OG1.JOB_STATE
--                                AND OG1.PREV_JOB_STATE IN
--                                    (
--                                        SELECT
--                                            SUBSTR (CSV, INSTR (CSV, ',', 1, LEV) + 1, INSTR (CSV, ',', 1,
--                                            LEV + 1) - INSTR (CSV, ',', 1, LEV) - 1) JOB_STATE
--                                        FROM
--                                            (
--                                                SELECT
--                                                    ',' || PARAM_VALUE || ',' CSV,
--                                                    PARAM_CODE
--                                                FROM
--                                                    GENERAL_PARAM
--                                                WHERE
--                                                    PARAM_CODE IN ( 'WIP_JOBSTATE_IA' ,
--                                                                   'WIP_JOBSTATE_DE1_1',
--                                                                   'WIP_JOBSTATE_DE1_2')),
--                                            (
--                                                SELECT
--                                                    LEVEL LEV
--                                                FROM
--                                                    DUAL CONNECT BY LEVEL <= 100)
--                                        WHERE
--                                            LEV <= LENGTH (CSV) - LENGTH (REPLACE (CSV, ',')) - 1))))
--                WHERE
--                    ((
--                            R.ROLE_NAME != 'VT'
--                        AND R.ROLE_NAME !='CA')
--                    OR  (
--                            R.ROLE_NAME != 'VT'
--                        AND EXISTS
--                            (
--                                SELECT
--                                    1
--                                FROM
--                                    ORIG_APPLICATION AP
--                                WHERE
--                                    AP.APPLICATION_GROUP_ID = OG1.APPLICATION_GROUP_ID
--                                AND (
--                                        LIFE_CYCLE > 1
--                                    OR  LAST_DECISION = 'RE') )) )
--                AND OG.APPLICATION_GROUP_ID = OG1.APPLICATION_GROUP_ID ) ;*/
--
--
--        SELECT
--            SUM(((V_INCOMING_APP_URGENT + V_ON_HAND_URGENT) * V_PERCENT_BUFFER)/(V_OT_HOUR * V_BASELINE) )
--            ,
--            SUM(((V_URGENT_D_1) * V_PERCENT_BUFFER)/(V_OT_HOUR_HOLIDAY * V_BASELINE)) ,
--            SUM(((V_URGENT_D_2) * V_PERCENT_BUFFER)/(V_OT_HOUR_HOLIDAY * V_BASELINE))
--        INTO
--            V_OT_CELL_TODAY,
--            V_OT_CELL_D_1,
--            V_OT_CELL_D_2
--        FROM
--            DUAL;
--
--
--        --URGENT APP(D+1) --
--        SELECT
--            DHB_SUMMARY_DATA_PK.NEXTVAL
--        INTO
--            V_SUMMARY_ID
--        FROM
--            DUAL ;
--        V_DHB_TYPE := '05';
--        V_POSITION_LEVEL := CUR.POSITION_ID;
--        V_DBH_OWNER := CUR.USER_ID;
--        V_DBH_GROUP := 1;
--        V_DBH_SEQ :=1;
--
--        INSERT
--        INTO
--            DHB_SUMMARY_DATA
--            (
--                SUMMARY_ID,
--                POSITION_LEVEL ,
--                DHB_TYPE ,
--                DHB_OWNER ,
--                DHB_GROUP,
--                DHP_SEQ,
--                VALUE1 ,
--                DESCRIPTION1,
--                VALUE2,
--                DESCRIPTION2 ,
--                VALUE3,
--                DESCRIPTION3 ,
--                VALUE4 ,
--                DESCRIPTION4,
--                CREATE_DATE,
--                STATUS
--            )
--            VALUES
--            (
--                V_SUMMARY_ID,
--                V_POSITION_LEVEL,
--                V_DHB_TYPE,
--                V_DBH_OWNER,
--                V_DBH_GROUP,
--                V_DBH_SEQ,
--                CEIL(NVL(V_URGENT_D_1,0)),
--                'Urgent app(D+1)',
--                NULL,
--                NULL,
--                NULL,
--                NULL,
--                NULL,
--                NULL,
--                V_APPLICATION_DATE,
--                'W'
--            );
--        COMMIT;
--
--        --URGENT APP(D+2) --
--        SELECT
--            DHB_SUMMARY_DATA_PK.NEXTVAL
--        INTO
--            V_SUMMARY_ID
--        FROM
--            DUAL ;
--        V_DHB_TYPE := '05';
--        V_POSITION_LEVEL := CUR.POSITION_ID;
--        V_DBH_OWNER := CUR.USER_ID;
--        V_DBH_GROUP := 1;
--        V_DBH_SEQ :=1;
--
--        INSERT
--        INTO
--            DHB_SUMMARY_DATA
--            (
--                SUMMARY_ID,
--                POSITION_LEVEL ,
--                DHB_TYPE ,
--                DHB_OWNER ,
--                DHB_GROUP,
--                DHP_SEQ,
--                VALUE1 ,
--                DESCRIPTION1,
--                VALUE2,
--                DESCRIPTION2 ,
--                VALUE3,
--                DESCRIPTION3 ,
--                VALUE4 ,
--                DESCRIPTION4,
--                CREATE_DATE,
--                STATUS
--            )
--            VALUES
--            (
--                V_SUMMARY_ID,
--                V_POSITION_LEVEL,
--                V_DHB_TYPE,
--                V_DBH_OWNER,
--                V_DBH_GROUP,
--                V_DBH_SEQ,
--                CEIL(NVL(V_URGENT_D_2,0)),
--                'Urgent app(D+2)',
--                NULL,
--                NULL,
--                NULL,
--                NULL,
--                NULL,
--                NULL,
--                V_APPLICATION_DATE,
--                'W'
--            );
--        COMMIT;
--
--        -- OT CALL TODAY --
--
--        SELECT
--            DHB_SUMMARY_DATA_PK.NEXTVAL
--        INTO
--            V_SUMMARY_ID
--        FROM
--            DUAL ;
--        V_DHB_TYPE := '06';
--        V_POSITION_LEVEL := CUR.POSITION_ID;
--        V_DBH_OWNER := CUR.USER_ID;
--        V_DBH_GROUP := 1;
--        V_DBH_SEQ :=1;
--
--        INSERT
--        INTO
--            DHB_SUMMARY_DATA
--            (
--                SUMMARY_ID,
--                POSITION_LEVEL ,
--                DHB_TYPE ,
--                DHB_OWNER ,
--                DHB_GROUP,
--                DHP_SEQ,
--                VALUE1 ,
--                DESCRIPTION1,
--                VALUE2,
--                DESCRIPTION2 ,
--                VALUE3,
--                DESCRIPTION3 ,
--                VALUE4 ,
--                DESCRIPTION4,
--                CREATE_DATE,
--                STATUS
--            )
--            VALUES
--            (
--                V_SUMMARY_ID,
--                V_POSITION_LEVEL,
--                V_DHB_TYPE,
--                V_DBH_OWNER,
--                V_DBH_GROUP,
--                V_DBH_SEQ,
--                CEIL(NVL(V_OT_CELL_TODAY,0)),
--                'OT Call Today',
--                NULL,
--                NULL,
--                NULL,
--                NULL,
--                NULL,
--                NULL,
--                V_APPLICATION_DATE,
--                'W'
--            );
--        COMMIT;
--        --OT CALL D + 1 --
--        SELECT
--            DHB_SUMMARY_DATA_PK.NEXTVAL
--        INTO
--            V_SUMMARY_ID
--        FROM
--            DUAL ;
--        V_DHB_TYPE := '06';
--        V_POSITION_LEVEL := CUR.POSITION_ID;
--        V_DBH_OWNER := CUR.USER_ID;
--        V_DBH_GROUP := 1;
--        V_DBH_SEQ :=2;
--
--        INSERT
--        INTO
--            DHB_SUMMARY_DATA
--            (
--                SUMMARY_ID,
--                POSITION_LEVEL ,
--                DHB_TYPE ,
--                DHB_OWNER ,
--                DHB_GROUP,
--                DHP_SEQ,
--                VALUE1 ,
--                DESCRIPTION1,
--                VALUE2,
--                DESCRIPTION2 ,
--                VALUE3,
--                DESCRIPTION3 ,
--                VALUE4 ,
--                DESCRIPTION4,
--                CREATE_DATE,
--                STATUS
--            )
--            VALUES
--            (
--                V_SUMMARY_ID,
--                V_POSITION_LEVEL,
--                V_DHB_TYPE,
--                V_DBH_OWNER,
--                V_DBH_GROUP,
--                V_DBH_SEQ,
--                CEIL(NVL(V_OT_CELL_D_1,0)),
--                'OT Call D+1',
--                NULL,
--                NULL,
--                NULL,
--                NULL,
--                NULL,
--                NULL,
--                V_APPLICATION_DATE,
--                'W'
--            );
--        COMMIT;
--
--        --OT CALL D + 2 --
--        SELECT
--            DHB_SUMMARY_DATA_PK.NEXTVAL
--        INTO
--            V_SUMMARY_ID
--        FROM
--            DUAL ;
--        V_DHB_TYPE := '06';
--        V_POSITION_LEVEL := CUR.POSITION_ID;
--        V_DBH_OWNER := CUR.USER_ID;
--        V_DBH_GROUP := 1;
--        V_DBH_SEQ :=3;
--
--        INSERT
--        INTO
--            DHB_SUMMARY_DATA
--            (
--                SUMMARY_ID,
--                POSITION_LEVEL ,
--                DHB_TYPE ,
--                DHB_OWNER ,
--                DHB_GROUP,
--                DHP_SEQ,
--                VALUE1 ,
--                DESCRIPTION1,
--                VALUE2,
--                DESCRIPTION2 ,
--                VALUE3,
--                DESCRIPTION3 ,
--                VALUE4 ,
--                DESCRIPTION4,
--                CREATE_DATE,
--                STATUS
--            )
--            VALUES
--            (
--                V_SUMMARY_ID,
--                V_POSITION_LEVEL,
--                V_DHB_TYPE,
--                V_DBH_OWNER,
--                V_DBH_GROUP,
--                V_DBH_SEQ,
--                CEIL(NVL(V_OT_CELL_D_2,0)),
--                'OT Call D+2',
--                NULL,
--                NULL,
--                NULL,
--                NULL,
--                NULL,
--                NULL,
--                V_APPLICATION_DATE,
--                'W'
--            );
--        COMMIT;
--    END LOOP;
--END P_GEN_DASHBOARD_05_06;

PROCEDURE P_GEN_DASHBOARD_07
AS
    V_DHB_TYPE VARCHAR2(2) := '01';
    V_POSITION_LEVEL VARCHAR2(3) := 'ALL';
    V_DBH_OWNER VARCHAR2(30) := 'ALL';
    V_DBH_GROUP NUMBER;
    V_DBH_SEQ NUMBER;
    V_SCHEDULE_ID NUMBER;
    V_SUMMARY_ID NUMBER;
    V_NUMBER_APP_OUT_STATION NUMBER;
    V_NUMBER_APP_OVER_STANDARD NUMBER;
    V_ARCHIEVEMENT_OLA NUMBER;
    V_ROLE_NAME VARCHAR2(100);

    V_WORKING_HR_END_KBANK VARCHAR2(5);
    V_WORKING_HR_END_PHR VARCHAR2(5);
    V_WORKING_HR_START_KBANK VARCHAR2(5);
    V_WORKING_HR_START_PHR VARCHAR2(5);
    V_APPLICATION_DATE DATE;
    V_ROLE_DISPLAY VARCHAR2(100);
BEGIN
    -- ALL --
    /* no need to delete data in procedure
    DELETE
    FROM
        DHB_SUMMARY_DATA
    WHERE
        DHB_TYPE = '07';*/

    --%ACHIEVEMENT OLA --
    --IA,DE1.1,DE1.2,DE2,DV1,DV2,CA,VT --
    SELECT MAX(SCHEDULE_ID) INTO V_SCHEDULE_ID
    FROM DHB_SCHEDULE;
    /*
    SELECT VALUE1
    INTO  V_WORKING_HR_END_KBANK
    FROM WORKFLOW_PARAM
    WHERE PARAM_CODE = 'WORKING_HR_END_KBANK';

    SELECT VALUE1
    INTO V_WORKING_HR_END_PHR
    FROM WORKFLOW_PARAM
    WHERE PARAM_CODE = 'WORKING_HR_END_PHR';

    SELECT VALUE1
    INTO V_WORKING_HR_START_KBANK
    FROM WORKFLOW_PARAM
    WHERE PARAM_CODE = 'WORKING_HR_START_KBANK';

    SELECT VALUE1
    INTO V_WORKING_HR_START_PHR
    FROM WORKFLOW_PARAM
    WHERE PARAM_CODE = 'WORKING_HR_START_PHR';

    SELECT TRUNC(APP_DATE) + (SYSDATE - TRUNC(SYSDATE))
    INTO V_APPLICATION_DATE
    FROM APPLICATION_DATE;
    */
        V_DHB_TYPE := '07';
        V_DBH_GROUP := 1;
        --IA --1--DE1.1-- 2--DE1.2-- 3--DV-- 4--VT-- 5--CA-- 6  --DE2-- 7--FU--8

  FOR V_DBH_SEQ IN 1..8
  LOOP
      CASE (V_DBH_SEQ)
      WHEN 1 THEN
          V_ROLE_NAME := 'IA';
          V_ROLE_DISPLAY := 'IA';
      WHEN 2 THEN
          V_ROLE_NAME := 'DE1_1';
          V_ROLE_DISPLAY := 'DE1.1';
      WHEN 3 THEN
          V_ROLE_NAME := 'DE1_2';
          V_ROLE_DISPLAY := 'DE1.2';
      WHEN 4 THEN
          V_ROLE_NAME := 'DV';
          V_ROLE_DISPLAY := 'DV';
      WHEN 5 THEN
          V_ROLE_NAME := 'VT';
          V_ROLE_DISPLAY := 'VT';
      WHEN 6 THEN
          V_ROLE_NAME := 'CA';
          V_ROLE_DISPLAY := 'CA';
      WHEN 7 THEN
          V_ROLE_NAME := 'DE2';
          V_ROLE_DISPLAY := 'DE2';
      WHEN 8 THEN
          V_ROLE_NAME := 'FU';
          V_ROLE_DISPLAY := 'FU';
      END CASE;


      FOR CUR IN (SELECT DISTINCT
                       U.USER_ID,
                      (U.POSITION_ID) POSITION_ID
                  FROM MS_USER_TEAM U
                  --JOIN ORIG_IAS.USER_ROLE UR ON U.USER_ID = UR.USER_NAME
                  --JOIN ORIG_IAS.ROLE R ON R.ROLE_ID = UR.ROLE_ID
                   --                    AND R.ROLE_NAME = V_ROLE_NAME
                  WHERE U.POSITION_ID IN (1,2,3))
      LOOP


            V_POSITION_LEVEL := CUR.POSITION_ID;
            V_DBH_OWNER := CUR.USER_ID;

            SELECT DHB_SUMMARY_DATA_PK.NEXTVAL
            INTO V_SUMMARY_ID
            FROM  DUAL ;

            SELECT SUM(NUMBER_APP_OUT_STATION)
                  ,SUM(NUMBER_APP_OVER_STANDARD)
            INTO
                V_NUMBER_APP_OUT_STATION
                ,V_NUMBER_APP_OVER_STANDARD
            FROM DHB_ROLE
           -- WHERE SCHEDULE_ID = V_SCHEDULE_ID
            WHERE ROLE_NAME =V_ROLE_NAME;

            IF V_NUMBER_APP_OUT_STATION > 0 THEN
                V_ARCHIEVEMENT_OLA := (V_NUMBER_APP_OUT_STATION - V_NUMBER_APP_OVER_STANDARD)/
                V_NUMBER_APP_OUT_STATION * 100;
            ELSE
                V_ARCHIEVEMENT_OLA := 0;
            END IF;

            INSERT
            INTO
                DHB_SUMMARY_DATA
                (
                    SUMMARY_ID,
                    POSITION_LEVEL ,
                    DHB_TYPE ,
                    DHB_OWNER ,
                    DHB_GROUP,
                    DHP_SEQ,
                    VALUE1 ,
                    DESCRIPTION1,
                    VALUE2,
                    DESCRIPTION2 ,
                    VALUE3,
                    DESCRIPTION3 ,
                    VALUE4 ,
                    DESCRIPTION4,
                    CREATE_DATE,
                    STATUS
                )
                VALUES
                (
                    V_SUMMARY_ID,
                    V_POSITION_LEVEL,
                    V_DHB_TYPE,
                    V_DBH_OWNER,
                    V_DBH_GROUP,
                    V_DBH_SEQ,
                    ROUND(V_ARCHIEVEMENT_OLA,2) ,
                    V_ROLE_DISPLAY,
                    NULL,
                    NULL,
                    NULL,
                    NULL,
                    NULL,
                    NULL,
                    V_APPLICATION_DATE,
                    'W'
                );
            COMMIT;
        END LOOP;--FOR CUR
    END LOOP;--FOR V_DBH_SEQ IN 1..8

END P_GEN_DASHBOARD_07;

PROCEDURE P_GEN_DASHBOARD_08
AS
  V_DHB_TYPE                     VARCHAR2(2)  := '01';
  V_POSITION_LEVEL               VARCHAR2(3)  := 'ALL';
  V_DBH_OWNER                    VARCHAR2(30) := 'ALL';
  V_DBH_GROUP                    NUMBER;
  V_DBH_SEQ                      NUMBER;
  V_SUMMARY_ID                   NUMBER;
  V_TOTAL_APP_OUTPUT_CUR_MONTH   NUMBER;
  V_TOTAL_APP_OVER_SLA_CUR_MONTH NUMBER;
  V_TOTAL_APP_OVER_OLA_CUR_MONTH NUMBER;
  V_SLA_ACHIEVEMENT              NUMBER;
  V_OLA_ACHIEVEMENT              NUMBER;
  V_SLA_TARGET                   NUMBER;
  V_LEG4                         NUMBER;
  -- [(Total application output in current month - Total application Over SLA target  in current month)  x 100]/Total application output in current month
  V_WORKING_HR_END_KBANK   VARCHAR2(5);
  V_WORKING_HR_END_PHR     VARCHAR2(5);
  V_WORKING_HR_START_KBANK VARCHAR2(5);
  V_WORKING_HR_START_PHR   VARCHAR2(5);
  v_application_date       DATE;
  V_MONTH_EN            VARCHAR2(20);
BEGIN
  -- ALL --
  SELECT TRUNC(APP_DATE) + (SYSDATE - TRUNC(SYSDATE))
  INTO V_APPLICATION_DATE
  FROM APPLICATION_DATE;

  SELECT VALUE1
  INTO V_WORKING_HR_END_KBANK
  FROM WORKFLOW_PARAM
  WHERE PARAM_CODE = 'WORKING_HR_END_KBANK';

  SELECT VALUE1
  INTO V_WORKING_HR_END_PHR
  FROM WORKFLOW_PARAM
  WHERE PARAM_CODE = 'WORKING_HR_END_PHR';

  SELECT VALUE1
  INTO V_WORKING_HR_START_KBANK
  FROM WORKFLOW_PARAM
  WHERE PARAM_CODE = 'WORKING_HR_START_KBANK';

  SELECT VALUE1
  INTO V_WORKING_HR_START_PHR
  FROM WORKFLOW_PARAM
  WHERE PARAM_CODE = 'WORKING_HR_START_PHR';
  /* no need to delete data in procedure
  delete from DHB_SUMMARY_DATA where dhb_type = '08'; */

  --%SLA  Achievement --
    WITH TEMP
         AS (SELECT /*+index(M,IDX1_DHB_LEG)*/
                   COUNT (DISTINCT A.APPLICATION_RECORD_ID) NUM
               FROM ORIG_APPLICATION_GROUP OG
                    JOIN ORIG_APPLICATION A ON OG.APPLICATION_GROUP_ID = A.APPLICATION_GROUP_ID
               WHERE OG.JOB_STATE IN ('NM9902','NM9903','NM9904') AND OG.APPLICATION_DATE >= TRUNC (SYSDATE, 'MONTH')
             UNION ALL
             SELECT COUNT (G.APPLICATION_GROUP_ID) NUM
               FROM ORIG_APPLICATION_GROUP G
              WHERE     G.JOB_STATE = 'NM9903'
                    AND G.APPLICATION_DATE >= TRUNC (SYSDATE, 'MONTH')
                    AND NOT EXISTS
                           (SELECT 1
                              FROM ORIG_APPLICATION A
                             WHERE G.APPLICATION_GROUP_ID = A.APPLICATION_GROUP_ID))
    SELECT SUM (NUM) INTO V_TOTAL_APP_OUTPUT_CUR_MONTH
      FROM TEMP;

  SELECT VALUE1
  INTO V_SLA_TARGET
  FROM REPORT_PARAM
  WHERE PARAM_TYPE = 'PERCENT_SLA'
  AND PARAM_CODE   = 'TARGET';

  SELECT VALUE1
  INTO V_LEG4
  FROM WORKFLOW_PARAM
  WHERE PARAM_CODE = 'OLA_LEG4';

  WITH TEMP
     AS (SELECT /*+index(M,IDX1_DHB_LEG)*/
               COUNT (DISTINCT A.APPLICATION_RECORD_ID) NUM
           FROM ORIG_APPLICATION_GROUP OG
                JOIN ORIG_APPLICATION A ON OG.APPLICATION_GROUP_ID = A.APPLICATION_GROUP_ID
                JOIN BUSINESS_CLASS B ON B.BUS_CLASS_ID = A.BUSINESS_CLASS_ID
                JOIN ORIG_LOAN L ON L.APPLICATION_RECORD_ID = A.APPLICATION_RECORD_ID
                JOIN ORIG_CARD C ON C.LOAN_ID = L.LOAN_ID
                JOIN CARD_TYPE CT ON CT.CARD_TYPE_ID = C.CARD_TYPE
                JOIN DHB_PRECAL_LEG M ON M.PRODUCT_TYPE = B.ORG_ID AND NVL (M.CARD_TYPE, 'XXX') = NVL (C.CARD_TYPE, 'XXX') AND M.INCREASE_FLAG = DECODE (OG.APPLICATION_TYPE, 'INC', 'Y', 'N')
          WHERE OG.APPLICATION_DATE >= TRUNC (SYSDATE, 'MONTH')
                AND (OG.LEG_TIME1+OG.LEG_TIME2+OG.LEG_TIME3+OG.LEG_TIME4) > (SELECT LEG1+LEG2+LEG3+LEG4 FROM MS_SLA WHERE SLA_CODE = 'CC_GEN')
         UNION ALL
         SELECT COUNT (G.APPLICATION_GROUP_ID) NUM
           FROM ORIG_APPLICATION_GROUP G
          WHERE     G.JOB_STATE IN ('NM9902','NM9903','NM9904')
                AND G.APPLICATION_DATE >= TRUNC (SYSDATE, 'MONTH')
                AND (G.LEG_TIME1+G.LEG_TIME2+G.LEG_TIME3+G.LEG_TIME4) > (SELECT LEG1+LEG2+LEG3+LEG4 FROM MS_SLA WHERE SLA_CODE = 'CC_GEN') --CANCELLED WITHOUT CARD EQUIVALENT TO CC GENERIC SLA
                AND NOT EXISTS
                       (SELECT 1
                          FROM ORIG_APPLICATION A
                         WHERE G.APPLICATION_GROUP_ID = A.APPLICATION_GROUP_ID))
    SELECT SUM (NUM) INTO V_TOTAL_APP_OVER_SLA_CUR_MONTH
      FROM TEMP;

  --COUNT TOTAL OVER SLA(LEG2)
  /* Formatted on 9/14/2017 7:09:01 PM (QP5 v5.294) */
    WITH TEMP
     AS (SELECT /*+index(M,IDX1_DHB_LEG)*/
               COUNT (DISTINCT A.APPLICATION_RECORD_ID) NUM
           FROM ORIG_APPLICATION_GROUP OG
                JOIN ORIG_APPLICATION A ON OG.APPLICATION_GROUP_ID = A.APPLICATION_GROUP_ID
                JOIN BUSINESS_CLASS B ON B.BUS_CLASS_ID = A.BUSINESS_CLASS_ID
                JOIN ORIG_LOAN L ON L.APPLICATION_RECORD_ID = A.APPLICATION_RECORD_ID
                JOIN ORIG_CARD C ON C.LOAN_ID = L.LOAN_ID
                JOIN CARD_TYPE CT ON CT.CARD_TYPE_ID = C.CARD_TYPE
                JOIN DHB_PRECAL_LEG M ON M.PRODUCT_TYPE = B.ORG_ID AND NVL (M.CARD_TYPE, 'XXX') = NVL (C.CARD_TYPE, 'XXX') AND M.INCREASE_FLAG = DECODE (OG.APPLICATION_TYPE, 'INC', 'Y', 'N')
          WHERE OG.APPLICATION_DATE >= TRUNC (SYSDATE, 'MONTH') AND OG.LEG_TIME2 > M.LEG2
         UNION ALL
         SELECT COUNT (G.APPLICATION_GROUP_ID) NUM
           FROM ORIG_APPLICATION_GROUP G
          WHERE     G.JOB_STATE = 'NM9903'
                AND G.APPLICATION_DATE >= TRUNC (SYSDATE, 'MONTH')
                AND G.LEG_TIME2 > (SELECT LEG2 FROM MS_SLA WHERE SLA_CODE = 'CC_GEN') --CANCELLED WITHOUT CARD EQUIVALENT TO CC GENERIC SLA
                AND NOT EXISTS
                       (SELECT 1
                          FROM ORIG_APPLICATION A
                         WHERE G.APPLICATION_GROUP_ID = A.APPLICATION_GROUP_ID))
    SELECT SUM (NUM) INTO V_TOTAL_APP_OVER_OLA_CUR_MONTH
      FROM TEMP;

  IF V_TOTAL_APP_OUTPUT_CUR_MONTH      = 0 THEN
    V_SLA_ACHIEVEMENT                 := 0;
    V_OLA_ACHIEVEMENT                 := 0;
  ELSE
    V_SLA_ACHIEVEMENT := (V_TOTAL_APP_OUTPUT_CUR_MONTH - V_TOTAL_APP_OVER_SLA_CUR_MONTH) * 100 / V_TOTAL_APP_OUTPUT_CUR_MONTH ;
    V_OLA_ACHIEVEMENT := (V_TOTAL_APP_OUTPUT_CUR_MONTH - V_TOTAL_APP_OVER_OLA_CUR_MONTH) * 100 / V_TOTAL_APP_OUTPUT_CUR_MONTH ;
  END IF;

  SELECT DHB_SUMMARY_DATA_PK.NEXTVAL
  INTO V_SUMMARY_ID
  FROM DUAL ;

  V_DHB_TYPE       := '08';
  V_POSITION_LEVEL := 'ALL';
  V_DBH_OWNER      := 'ALL';
  V_DBH_GROUP      := 1;
  V_DBH_SEQ        :=1;

  INSERT
  INTO DHB_SUMMARY_DATA
    (
      SUMMARY_ID,
      POSITION_LEVEL ,
      DHB_TYPE ,
      DHB_OWNER ,
      DHB_GROUP,
      DHP_SEQ,
      VALUE1 ,
      DESCRIPTION1,
      VALUE2,
      DESCRIPTION2 ,
      VALUE3,
      DESCRIPTION3 ,
      VALUE4 ,
      DESCRIPTION4,
      CREATE_DATE,
      STATUS
    )
    VALUES
    (
      V_SUMMARY_ID,
      V_POSITION_LEVEL,
      V_DHB_TYPE,
      V_DBH_OWNER,
      V_DBH_GROUP,
      V_DBH_SEQ,
      ROUND(V_SLA_ACHIEVEMENT,2) ,
      '%SLA Achievement',
      NULL,
      '%SLA Achievement',
      NULL,
      NULL,
      NULL,
      NULL,
      V_APPLICATION_DATE,
      'W'
    );
  COMMIT;

  SELECT DHB_SUMMARY_DATA_PK.NEXTVAL INTO V_SUMMARY_ID FROM DUAL ;
  V_DHB_TYPE       := '08';
  V_POSITION_LEVEL := 'ALL';
  V_DBH_OWNER      := 'ALL';
  V_DBH_GROUP      := 1;
  V_DBH_SEQ        :=2;
  INSERT
  INTO DHB_SUMMARY_DATA
    (
      SUMMARY_ID,
      POSITION_LEVEL ,
      DHB_TYPE ,
      DHB_OWNER ,
      DHB_GROUP,
      DHP_SEQ,
      VALUE1 ,
      DESCRIPTION1,
      VALUE2,
      DESCRIPTION2 ,
      VALUE3,
      DESCRIPTION3 ,
      VALUE4 ,
      DESCRIPTION4,
      CREATE_DATE,
      STATUS
    )
    VALUES
    (
      V_SUMMARY_ID,
      V_POSITION_LEVEL,
      V_DHB_TYPE,
      V_DBH_OWNER,
      V_DBH_GROUP,
      V_DBH_SEQ,
      ROUND(V_OLA_ACHIEVEMENT,2) ,
      '%OLA Achievement',
      NULL,
      '%OLA Achievement',
      NULL,
      NULL,
      NULL,
      NULL,
      V_APPLICATION_DATE,
      'W'
    );
  COMMIT;

  INSERT
  INTO DHB_SUMMARY_DATA
    (
      SUMMARY_ID,
      POSITION_LEVEL ,
      DHB_TYPE ,
      DHB_OWNER ,
      DHB_GROUP,
      DHP_SEQ,
      VALUE1 ,
      DESCRIPTION1,
      VALUE2,
      DESCRIPTION2 ,
      VALUE3,
      DESCRIPTION3 ,
      VALUE4 ,
      DESCRIPTION4,
      CREATE_DATE,
      STATUS
    )
  SELECT DHB_SUMMARY_DATA_pk.nextval,
    position_id ,
    d.DHB_TYPE ,
    mu.user_id ,
    d.DHB_GROUP ,
    d.DHP_SEQ,
    d.VALUE1 ,
    d.DESCRIPTION1,
    d.VALUE2,
    d.DESCRIPTION2 ,
    d.VALUE3,
    d.DESCRIPTION3 ,
    d.VALUE4 ,
    d.DESCRIPTION4,
    d.CREATE_DATE,
    'W'
  FROM DHB_SUMMARY_DATA d
  JOIN
    (SELECT DISTINCT USER_ID ,
      POSITION_ID
    FROM MS_USER_TEAM MU
    WHERE MU.POSITION_ID BETWEEN 1 AND 4
    ) MU
  ON 1           =1
  WHERE DHB_TYPE = '08'
  AND DHB_OWNER  = 'ALL'
  AND STATUS     = 'W';
  COMMIT;

  --Start insert SLA OLA
      SELECT DHB_SUMMARY_DATA_PK.NEXTVAL
      INTO V_SUMMARY_ID
      FROM DUAL ;

      V_DHB_TYPE       := '10';
      V_POSITION_LEVEL := 'ALL';
      V_DBH_OWNER      := 'ALL';
      V_DBH_GROUP      := 1;
      V_DBH_SEQ        :=6;
      V_MONTH_EN    := F_GET_MON(V_APPLICATION_DATE);

      INSERT
      INTO DHB_SUMMARY_DATA
        (
          SUMMARY_ID,
          POSITION_LEVEL ,
          DHB_TYPE ,
          DHB_OWNER ,
          DHB_GROUP,
          DHP_SEQ,
          VALUE1 ,
          DESCRIPTION1,
          VALUE2,
          DESCRIPTION2 ,
          VALUE3,
          DESCRIPTION3 ,
          VALUE4 ,
          DESCRIPTION4,
          CREATE_DATE,
          STATUS
        )
        VALUES
        (
          V_SUMMARY_ID,
          V_POSITION_LEVEL,
          V_DHB_TYPE,
          V_DBH_OWNER,
          V_DBH_GROUP,
          V_DBH_SEQ,
          ROUND(V_SLA_ACHIEVEMENT,2),
          V_MONTH_EN,
          NULL,
          'SLA',
          NULL,
          NULL,
          NULL,
          NULL,
          V_APPLICATION_DATE,
          'W'
        );
      COMMIT;

      SELECT DHB_SUMMARY_DATA_PK.NEXTVAL
      INTO V_SUMMARY_ID
      FROM DUAL ;

      V_DHB_TYPE       := '10';
      V_POSITION_LEVEL := 'ALL';
      V_DBH_OWNER      := 'ALL';
      V_DBH_GROUP      := 2;
      V_DBH_SEQ        := 6;

      INSERT
      INTO DHB_SUMMARY_DATA
        (
          SUMMARY_ID,
          POSITION_LEVEL ,
          DHB_TYPE ,
          DHB_OWNER ,
          DHB_GROUP,
          DHP_SEQ,
          VALUE1 ,
          DESCRIPTION1,
          VALUE2,
          DESCRIPTION2 ,
          VALUE3,
          DESCRIPTION3 ,
          VALUE4 ,
          DESCRIPTION4,
          CREATE_DATE,
          STATUS
        )
        VALUES
        (
          V_SUMMARY_ID,
          V_POSITION_LEVEL,
          V_DHB_TYPE,
          V_DBH_OWNER,
          V_DBH_GROUP,
          V_DBH_SEQ,
          ROUND(V_OLA_ACHIEVEMENT,2) ,
          V_MONTH_EN,
          NULL,
          'OLA',
          NULL,
          NULL,
          NULL,
          NULL,
          V_APPLICATION_DATE,
          'W'
        );
      COMMIT;

  --End insert SLA OLA

   --Start insert into  OLA Leg2 (11)
  SELECT DHB_SUMMARY_DATA_PK.NEXTVAL
  INTO V_SUMMARY_ID
  FROM DUAL ;

  V_DHB_TYPE                     := '11';
  V_POSITION_LEVEL               := 'ALL';
  V_DBH_OWNER                    := 'ALL';
  V_DBH_GROUP                    := 1;
  V_DBH_SEQ                      :=2;


    INSERT
    INTO DHB_SUMMARY_DATA
      (
        SUMMARY_ID,
        POSITION_LEVEL ,
        DHB_TYPE ,
        DHB_OWNER ,
        DHB_GROUP,
        DHP_SEQ,
        VALUE1 ,
        DESCRIPTION1,
        VALUE2,
        DESCRIPTION2 ,
        VALUE3,
        DESCRIPTION3 ,
        VALUE4 ,
        DESCRIPTION4,
        CREATE_DATE,
        STATUS
      )
      VALUES
      (
        V_SUMMARY_ID,
        V_POSITION_LEVEL,
        V_DHB_TYPE,
        V_DBH_OWNER,
        V_DBH_GROUP,
        V_DBH_SEQ,
        ROUND(V_OLA_ACHIEVEMENT,2) ,
        '% OLA Achievement',
        NULL,
        'Leg 2',
        NULL,
        NULL,
        NULL,
        NULL,
        V_APPLICATION_DATE,
        'W'
      );

   --End insert into  OLA Leg2 (11)
END;

PROCEDURE P_GEN_DASHBOARD_09
AS
  V_DHB_TYPE         VARCHAR2(2)  := '01';
  V_POSITION_LEVEL   VARCHAR2(3)  := 'ALL';
  V_DBH_OWNER        VARCHAR2(30) := 'ALL';
  V_DBH_GROUP        NUMBER;
  V_DBH_SEQ          NUMBER;
  V_SUMMARY_ID       NUMBER;
  V_ROLE_NAME        VARCHAR2(100);
  V_COUNT_APP_URGENT NUMBER;
  V_COUNT_APP_NORMAL NUMBER;
  V_APPLICATION_DATE DATE;
  V_ROLE_DISPLAY     VARCHAR2(100);
  V_STATION_APP T_STATION_APP;
BEGIN
  -- ALL --
  SELECT TRUNC(APP_DATE) + (SYSDATE - TRUNC(SYSDATE))
  INTO V_APPLICATION_DATE
  FROM APPLICATION_DATE;
  /* NO NEED TO DELETE DATA IN PROCEDURE
  DELETE FROM DHB_SUMMARY_DATA WHERE DHB_TYPE = '09';*/
  --WORK IN PROCESS PER STATION--
  FOR CUR IN
  (SELECT DISTINCT USER_ID,
    (POSITION_ID) POSITION_ID
  FROM MS_USER_TEAM
  WHERE POSITION_ID IN (1,2)
  )
  LOOP
    --IA --1--DE1.1-- 2--DE1.2-- 3--DV-- 4--VT-- 5--CA-- 6  --DE2-- 7--FU--8
    FOR V_DBH_SEQ IN 1..8
    LOOP
      CASE (V_DBH_SEQ)
      WHEN 1 THEN
        V_ROLE_NAME    := 'IA';
        V_ROLE_DISPLAY := 'IA';
      WHEN 2 THEN
        V_ROLE_NAME    := 'DE1_1';
        V_ROLE_DISPLAY := 'DE1.1';
      WHEN 3 THEN
        V_ROLE_NAME    := 'DE1_2';
        V_ROLE_DISPLAY := 'DE1.2';
      WHEN 4 THEN
        V_ROLE_NAME    := 'DV';
        V_ROLE_DISPLAY := 'DV';
      WHEN 5 THEN
        V_ROLE_NAME    := 'VT';
        V_ROLE_DISPLAY := 'VT';
      WHEN 6 THEN
        V_ROLE_NAME    := 'CA';
        V_ROLE_DISPLAY := 'CA';
      WHEN 7 THEN
        V_ROLE_NAME    := 'DE2';
        V_ROLE_DISPLAY := 'DE2';
      WHEN 8 THEN
        V_ROLE_NAME    := 'FU';
        V_ROLE_DISPLAY := 'FU';
      END CASE;
      V_DHB_TYPE       := '09';
      V_POSITION_LEVEL := CUR.POSITION_ID;
      V_DBH_OWNER      := CUR.USER_ID;

--      SELECT COUNT(DISTINCT
--        CASE
--          WHEN OG.PRIORITY IN (3,4)
--          THEN og.APPLICATION_GROUP_ID
--          ELSE NULL
--        END) ,
--        COUNT(DISTINCT
--        CASE
--          WHEN OG.PRIORITY IN (1,2)
--          THEN og.APPLICATION_GROUP_ID
--          ELSE NULL
--        END)
--      INTO V_COUNT_APP_NORMAL,
--        V_COUNT_APP_URGENT
--      FROM ORIG_APPLICATION_GROUP OG
--      JOIN DHB_JOB_STATE T
--      ON T.JOB_STATE   = OG.JOB_STATE
--      AND T.PARAM_CODE = 'WIP_JOBSTATE_'
--        || V_ROLE_NAME;
      V_STATION_APP := F_COUNT_WIP_BY_ROLE(V_ROLE_NAME, NULL);
      V_COUNT_APP_NORMAL := V_STATION_APP.WIP_NORMAL;
      V_COUNT_APP_URGENT := V_STATION_APP.WIP_URGENT;

      SELECT DHB_SUMMARY_DATA_PK.NEXTVAL
      INTO V_SUMMARY_ID
      FROM DUAL ;

      V_DBH_GROUP := 1;

      INSERT
      INTO DHB_SUMMARY_DATA
        (
          SUMMARY_ID,
          POSITION_LEVEL ,
          DHB_TYPE ,
          DHB_OWNER ,
          DHB_GROUP,
          DHP_SEQ,
          VALUE1 ,
          DESCRIPTION1,
          VALUE2,
          DESCRIPTION2 ,
          VALUE3,
          DESCRIPTION3 ,
          VALUE4 ,
          DESCRIPTION4,
          CREATE_DATE,
          STATUS
        )
        VALUES
        (
          V_SUMMARY_ID,
          V_POSITION_LEVEL,
          V_DHB_TYPE,
          V_DBH_OWNER,
          V_DBH_GROUP,
          V_DBH_SEQ,
          V_COUNT_APP_URGENT,
          V_ROLE_DISPLAY,
          NULL,
          'urgent',
          NULL,
          NULL,
          NULL,
          NULL,
          V_APPLICATION_DATE,
          'W'
        );
      COMMIT;

      SELECT DHB_SUMMARY_DATA_PK.NEXTVAL INTO V_SUMMARY_ID FROM DUAL ;
      V_DBH_GROUP := 2;
      INSERT
      INTO DHB_SUMMARY_DATA
        (
          SUMMARY_ID,
          POSITION_LEVEL ,
          DHB_TYPE ,
          DHB_OWNER ,
          DHB_GROUP,
          DHP_SEQ,
          VALUE1 ,
          DESCRIPTION1,
          VALUE2,
          DESCRIPTION2 ,
          VALUE3,
          DESCRIPTION3 ,
          VALUE4 ,
          DESCRIPTION4,
          CREATE_DATE,
          STATUS
        )
        VALUES
        (
          V_SUMMARY_ID,
          V_POSITION_LEVEL,
          V_DHB_TYPE,
          V_DBH_OWNER,
          V_DBH_GROUP,
          V_DBH_SEQ,
          V_COUNT_APP_NORMAL,
          V_ROLE_DISPLAY,
          NULL,
          'normal',
          NULL,
          NULL,
          NULL,
          NULL,
          V_APPLICATION_DATE,
          'W'
        );
      COMMIT;
    END LOOP;
  END LOOP;
END;

PROCEDURE P_GEN_DASHBOARD_10
AS
  V_DHB_TYPE                     VARCHAR2(2)  := '01';
  V_POSITION_LEVEL               VARCHAR2(3)  := 'ALL';
  V_DBH_OWNER                    VARCHAR2(30) := 'ALL';
  V_DBH_GROUP                    NUMBER;
  V_DBH_SEQ                      NUMBER;
  V_SUMMARY_ID                   NUMBER;
  V_TOTAL_APP_OUTPUT_CUR_MONTH   NUMBER;
  V_TOTAL_APP_OVER_SLA_CUR_MONTH NUMBER;
  V_TOTAL_APP_OVER_OLA_CUR_MONTH NUMBER;
  V_SLA_ACHIEVEMENT              NUMBER;
  V_OLA_ACHIEVEMENT              NUMBER;
  V_SLA_TARGET                   NUMBER;
  V_OLA_TARGET                   NUMBER;
  V_LEG4                         NUMBER;
  V_SEQ                          NUMBER := 0;
  -- [(TOTAL APPLICATION OUTPUT IN CURRENT MONTH - TOTAL APPLICATION OVER SLA TARGET  IN CURRENT MONTH)  X 100]/TOTAL APPLICATION OUTPUT IN CURRENT MONTH
  V_WORKING_HR_END_KBANK   VARCHAR2(5);
  V_WORKING_HR_END_PHR     VARCHAR2(5);
  V_WORKING_HR_START_KBANK VARCHAR2(5);
  V_WORKING_HR_START_PHR   VARCHAR2(5);
  V_APPLICATION_DATE       DATE;
  V_MONTH_EN               VARCHAR2(3);
  V_OLD_DATA_ID            NUMBER;
BEGIN
  -- ALL --
  SELECT TRUNC(APP_DATE) + (SYSDATE - TRUNC(SYSDATE))
  INTO V_APPLICATION_DATE
  FROM APPLICATION_DATE;

  SELECT VALUE1
  INTO V_WORKING_HR_END_KBANK
  FROM WORKFLOW_PARAM
  WHERE PARAM_CODE = 'WORKING_HR_END_KBANK';

  SELECT VALUE1
  INTO V_WORKING_HR_END_PHR
  FROM WORKFLOW_PARAM
  WHERE PARAM_CODE = 'WORKING_HR_END_PHR';

  SELECT VALUE1
  INTO V_WORKING_HR_START_KBANK
  FROM WORKFLOW_PARAM
  WHERE PARAM_CODE = 'WORKING_HR_START_KBANK';

  SELECT VALUE1
  INTO V_WORKING_HR_START_PHR
  FROM WORKFLOW_PARAM
  WHERE PARAM_CODE = 'WORKING_HR_START_PHR';

  SELECT VALUE1
  INTO V_SLA_TARGET
  FROM REPORT_PARAM
  WHERE PARAM_TYPE = 'PERCENT_SLA'
  AND PARAM_CODE   ='TARGET';

  SELECT VALUE1
  INTO V_OLA_TARGET
  FROM REPORT_PARAM
  WHERE PARAM_TYPE = 'PERCENT_OLA_TARGET'
  AND PARAM_CODE   ='LEG2';
  /* no need to delete data in procedure
  delete from DHB_SUMMARY_DATA where dhb_type = '10';*/
  V_SEQ := 0;
  FOR I IN TO_NUMBER(TO_CHAR(ADD_MONTHS(V_APPLICATION_DATE,-5),'YYYYMM')) .. TO_NUMBER(TO_CHAR(V_APPLICATION_DATE,'YYYYMM'))
  LOOP
    IF (SUBSTR(i,5,2)) BETWEEN '01' AND '12' THEN
      v_seq := v_seq + 1;
      CASE (SUBSTR(i,5,2))
      WHEN '01' THEN
        v_month_en := 'Jan';
      WHEN '02' THEN
        v_month_en := 'Feb';
      WHEN '03' THEN
        v_month_en := 'Mar';
      WHEN '04' THEN
        v_month_en := 'Apr';
      WHEN '05' THEN
        v_month_en := 'May';
      WHEN '06' THEN
        v_month_en := 'Jun';
      WHEN '07' THEN
        v_month_en := 'Jul';
      WHEN '08' THEN
        v_month_en := 'Aug';
      WHEN '09' THEN
        v_month_en := 'Sep';
      WHEN '10' THEN
        v_month_en := 'Oct';
      WHEN '11' THEN
        v_month_en := 'Nov';
      WHEN '12' THEN
        v_month_en := 'Dec';
      END CASE;


    IF V_SEQ <> 6 THEN

       -- REUSE OLD DATA
      V_OLD_DATA_ID := 0;
      BEGIN
        UPDATE DHB_SUMMARY_DATA SET STATUS = 'W', CREATE_DATE = V_APPLICATION_DATE, DHB_OWNER = 'ALL'   --INDICATE THIS DATA WILL BE AVAILABLE FOR EVERYONE
            ,DHP_SEQ = V_SEQ
        WHERE SUMMARY_ID IN (SELECT SUMMARY_ID
                        FROM DHB_SUMMARY_DATA SS
                       WHERE     SS.DHB_TYPE = '10'
                             AND STATUS = 'A'
                             AND TRUNC (CREATE_DATE) > ADD_MONTHS (SYSDATE, -5)
                             AND DESCRIPTION1 = v_month_en      --PICK CURRENT INDEX MONTH
                             AND DHB_OWNER = (     SELECT DHB_OWNER
                                                     FROM DHB_SUMMARY_DATA
                                                    WHERE DHB_TYPE = '10'
                                              FETCH FIRST 1 ROW ONLY)
                       ORDER BY SUMMARY_ID FETCH FIRST 4 ROWS ONLY);

        V_OLD_DATA_ID := SQL%ROWCOUNT;
      EXCEPTION WHEN OTHERS THEN
        V_OLD_DATA_ID := 0;
      END;

      IF V_OLD_DATA_ID > 0 THEN
        CONTINUE;--REUSE OLD DATA
      END IF;


      --%SLA  Achievement --
      SELECT /*+INDEX (LT, IDX19_LSW_TASK_TID_U)*/
      COUNT( DISTINCT OG.APPLICATION_GROUP_ID)
      INTO V_TOTAL_APP_OUTPUT_CUR_MONTH
      FROM LSW_TASK LT
      JOIN LSW_USR_XREF LU
      ON LU.USER_ID = LT.USER_ID
      JOIN ORIG_APPLICATION_GROUP OG
      ON OG.INSTANT_ID  = LT.BPD_INSTANCE_ID
      AND OG.JOB_STATE IN ('NM9902','NM9903','NM9904')
      JOIN ORIG_APPLICATION A
      ON A.APPLICATION_GROUP_ID = OG.APPLICATION_GROUP_ID
      JOIN BUSINESS_CLASS B
      ON B.BUS_CLASS_ID                    = A.BUSINESS_CLASS_ID
      WHERE LT.STATUS                      = '32'
      AND TO_CHAR(CLOSE_DATETIME,'YYYYMM') = I;

      SELECT VALUE1
      INTO V_SLA_TARGET
      FROM REPORT_PARAM
      WHERE PARAM_TYPE = 'PERCENT_SLA'
      AND PARAM_CODE   = 'TARGET';

      SELECT VALUE1
      INTO V_LEG4
      FROM WORKFLOW_PARAM
      WHERE PARAM_CODE = 'OLA_LEG4';

      SELECT COUNT( DISTINCT OG.APPLICATION_GROUP_ID)
      INTO V_TOTAL_APP_OVER_SLA_CUR_MONTH
      FROM LSW_TASK LT
      JOIN ORIG_APPLICATION_GROUP OG
      ON OG.INSTANT_ID                                                   = LT.BPD_INSTANCE_ID
      AND OG.JOB_STATE                                                  IN ('NM9902','NM9903','NM9904')
      WHERE ( OG.LEG_TIME1 + OG.LEG_TIME2 + OG.LEG_TIME3 + OG.LEG_TIME4) >
        (SELECT /*+INDEX(M,IDX1_DHB_LEG)*/ MAX(M.LEG1 + M.LEG2 + M.LEG3 + M.LEG4)
        FROM ORIG_APPLICATION A
        JOIN BUSINESS_CLASS B
        ON B.BUS_CLASS_ID = A.BUSINESS_CLASS_ID
        JOIN ORIG_LOAN L
        ON L.APPLICATION_RECORD_ID = A.APPLICATION_RECORD_ID
        JOIN ORIG_CARD C
        ON C.LOAN_ID = L.LOAN_ID
        JOIN CARD_TYPE CT
        ON CT.CARD_TYPE_ID = C.CARD_TYPE
        JOIN DHB_PRECAL_LEG M
        ON M.PRODUCT_TYPE  = B.ORG_ID
        AND NVL(M.CARD_TYPE,'XXX') = NVL(C.CARD_TYPE,'XXX')
        AND M.INCREASE_FLAG                    = DECODE(OG.APPLICATION_TYPE,'INC','Y','N')
        AND A.APPLICATION_GROUP_ID             = OG.APPLICATION_GROUP_ID
        )
      AND LT.STATUS                        = '32'
      AND TO_CHAR(CLOSE_DATETIME,'YYYYMM') = I ;

      SELECT COUNT( DISTINCT OG.APPLICATION_GROUP_ID)
      INTO V_TOTAL_APP_OVER_OLA_CUR_MONTH
      FROM LSW_TASK LT
      JOIN ORIG_APPLICATION_GROUP OG
      ON OG.INSTANT_ID      = LT.BPD_INSTANCE_ID
      AND OG.JOB_STATE     IN ('NM9902','NM9903','NM9904')
      WHERE ( OG.LEG_TIME2) >
        (SELECT /*+INDEX(M,IDX1_DHB_LEG)*/
        MAX(M.LEG2)
        FROM ORIG_APPLICATION A
        JOIN BUSINESS_CLASS B
        ON B.BUS_CLASS_ID = A.BUSINESS_CLASS_ID
        JOIN ORIG_LOAN L
        ON L.APPLICATION_RECORD_ID = A.APPLICATION_RECORD_ID
        JOIN ORIG_CARD C
        ON C.LOAN_ID = L.LOAN_ID
        JOIN CARD_TYPE CT
        ON CT.CARD_TYPE_ID = C.CARD_TYPE
        JOIN DHB_PRECAL_LEG M
        ON M.PRODUCT_TYPE  = B.ORG_ID
        AND NVL(M.CARD_TYPE,'XXX') = NVL(C.CARD_TYPE,'XXX')
        AND M.INCREASE_FLAG                    = DECODE(OG.APPLICATION_TYPE,'INC','Y','N')
        AND A.APPLICATION_GROUP_ID             = OG.APPLICATION_GROUP_ID
        )
      AND LT.STATUS                        = '32'
      AND TO_CHAR(CLOSE_DATETIME,'YYYYMM') = I ;

      IF V_TOTAL_APP_OUTPUT_CUR_MONTH      = 0 THEN
        V_SLA_ACHIEVEMENT                 := 0;
        V_OLA_ACHIEVEMENT                 := 0;
      ELSE
        V_SLA_ACHIEVEMENT := (V_TOTAL_APP_OUTPUT_CUR_MONTH - V_TOTAL_APP_OVER_SLA_CUR_MONTH) * 100 / V_TOTAL_APP_OUTPUT_CUR_MONTH ;
        V_OLA_ACHIEVEMENT := (V_TOTAL_APP_OUTPUT_CUR_MONTH - V_TOTAL_APP_OVER_OLA_CUR_MONTH) * 100 / V_TOTAL_APP_OUTPUT_CUR_MONTH ;
      END IF;

      SELECT DHB_SUMMARY_DATA_PK.NEXTVAL
      INTO V_SUMMARY_ID
      FROM DUAL ;

      V_DHB_TYPE       := '10';
      V_POSITION_LEVEL := 'ALL';
      V_DBH_OWNER      := 'ALL';
      V_DBH_GROUP      := 1;
      V_DBH_SEQ        :=V_SEQ;

      INSERT
      INTO DHB_SUMMARY_DATA
        (
          SUMMARY_ID,
          POSITION_LEVEL ,
          DHB_TYPE ,
          DHB_OWNER ,
          DHB_GROUP,
          DHP_SEQ,
          VALUE1 ,
          DESCRIPTION1,
          VALUE2,
          DESCRIPTION2 ,
          VALUE3,
          DESCRIPTION3 ,
          VALUE4 ,
          DESCRIPTION4,
          CREATE_DATE,
          STATUS
        )
        VALUES
        (
          V_SUMMARY_ID,
          V_POSITION_LEVEL,
          V_DHB_TYPE,
          V_DBH_OWNER,
          V_DBH_GROUP,
          V_DBH_SEQ,
          ROUND(V_SLA_ACHIEVEMENT,2),
          V_MONTH_EN,
          NULL,
          'SLA',
          NULL,
          NULL,
          NULL,
          NULL,
          V_APPLICATION_DATE,
          'W'
        );
      COMMIT;

      SELECT DHB_SUMMARY_DATA_PK.NEXTVAL
      INTO V_SUMMARY_ID
      FROM DUAL ;

      V_DHB_TYPE       := '10';
      V_POSITION_LEVEL := 'ALL';
      V_DBH_OWNER      := 'ALL';
      V_DBH_GROUP      := 2;
      V_DBH_SEQ        := V_SEQ;

      INSERT
      INTO DHB_SUMMARY_DATA
        (
          SUMMARY_ID,
          POSITION_LEVEL ,
          DHB_TYPE ,
          DHB_OWNER ,
          DHB_GROUP,
          DHP_SEQ,
          VALUE1 ,
          DESCRIPTION1,
          VALUE2,
          DESCRIPTION2 ,
          VALUE3,
          DESCRIPTION3 ,
          VALUE4 ,
          DESCRIPTION4,
          CREATE_DATE,
          STATUS
        )
        VALUES
        (
          V_SUMMARY_ID,
          V_POSITION_LEVEL,
          V_DHB_TYPE,
          V_DBH_OWNER,
          V_DBH_GROUP,
          V_DBH_SEQ,
          ROUND(V_OLA_ACHIEVEMENT,2),
          V_MONTH_EN,
          NULL,
          'OLA',
          NULL,
          NULL,
          NULL,
          NULL,
          V_APPLICATION_DATE,
          'W'
        );
      COMMIT;

    END IF; -- IF V_SEQ <> 6 THEN

        --Insert OLA/SLA Target
      SELECT DHB_SUMMARY_DATA_PK.NEXTVAL
      INTO V_SUMMARY_ID
      FROM DUAL ;

      V_DHB_TYPE       := '10';
      V_POSITION_LEVEL := 'ALL';
      V_DBH_OWNER      := 'ALL';
      V_DBH_GROUP      := 3;
      V_DBH_SEQ        := V_SEQ;

      INSERT
      INTO DHB_SUMMARY_DATA
        (
          SUMMARY_ID,
          POSITION_LEVEL ,
          DHB_TYPE ,
          DHB_OWNER ,
          DHB_GROUP,
          DHP_SEQ,
          VALUE1 ,
          DESCRIPTION1,
          VALUE2,
          DESCRIPTION2 ,
          VALUE3,
          DESCRIPTION3 ,
          VALUE4 ,
          DESCRIPTION4,
          CREATE_DATE,
          STATUS
        )
        VALUES
        (
          V_SUMMARY_ID,
          V_POSITION_LEVEL,
          V_DHB_TYPE,
          V_DBH_OWNER,
          V_DBH_GROUP,
          V_DBH_SEQ,
          ROUND(V_SLA_TARGET,2),
          V_MONTH_EN,
          NULL,
          'SLA TARGET',
          NULL,
          NULL,
          NULL,
          NULL,
          V_APPLICATION_DATE,
          'W'
        );
      COMMIT;

      SELECT DHB_SUMMARY_DATA_PK.NEXTVAL
      INTO V_SUMMARY_ID
      FROM DUAL ;

      V_DHB_TYPE       := '10';
      V_POSITION_LEVEL := 'ALL';
      V_DBH_OWNER      := 'ALL';
      V_DBH_GROUP      := 4;
      V_DBH_SEQ        := V_SEQ;

      INSERT
      INTO DHB_SUMMARY_DATA
        (
          SUMMARY_ID,
          POSITION_LEVEL ,
          DHB_TYPE ,
          DHB_OWNER ,
          DHB_GROUP,
          DHP_SEQ,
          VALUE1 ,
          DESCRIPTION1,
          VALUE2,
          DESCRIPTION2 ,
          VALUE3,
          DESCRIPTION3 ,
          VALUE4 ,
          DESCRIPTION4,
          CREATE_DATE,
          STATUS
        )
        VALUES
        (
          V_SUMMARY_ID,
          V_POSITION_LEVEL,
          V_DHB_TYPE,
          V_DBH_OWNER,
          V_DBH_GROUP,
          V_DBH_SEQ,
          ROUND(V_OLA_TARGET,2),
          V_MONTH_EN,
          NULL,
          'OLA TARGET',
          NULL,
          NULL,
          NULL,
          NULL,
          V_APPLICATION_DATE,
          'W'
        );
      COMMIT;
    END IF;
  END LOOP;

  --Duplicate data for DHB_OWNER = ALL
  INSERT
  INTO DHB_SUMMARY_DATA
    (
      SUMMARY_ID,
      POSITION_LEVEL ,
      DHB_TYPE ,
      DHB_OWNER ,
      DHB_GROUP,
      DHP_SEQ,
      VALUE1 ,
      DESCRIPTION1,
      VALUE2,
      DESCRIPTION2 ,
      VALUE3,
      DESCRIPTION3 ,
      VALUE4 ,
      DESCRIPTION4,
      CREATE_DATE,
      STATUS
    )
  SELECT DHB_SUMMARY_DATA_PK.NEXTVAL,
    POSITION_ID ,
    D.DHB_TYPE ,
    MU.USER_ID ,
    D.DHB_GROUP ,
    D.DHP_SEQ,
    D.VALUE1 ,
    D.DESCRIPTION1,
    D.VALUE2,
    D.DESCRIPTION2 ,
    D.VALUE3,
    D.DESCRIPTION3 ,
    D.VALUE4 ,
    D.DESCRIPTION4,
    D.CREATE_DATE,
    'W'
  FROM DHB_SUMMARY_DATA D
  JOIN
    (SELECT DISTINCT USER_ID ,
      POSITION_ID
    FROM MS_USER_TEAM MU
    WHERE MU.POSITION_ID BETWEEN 1 AND 2
    ) MU
  ON 1           =1
  WHERE DHB_TYPE = '10'
  AND DHB_OWNER  = 'ALL'
  AND STATUS     = 'W';
  COMMIT;
END;

PROCEDURE P_GEN_DASHBOARD_11
AS
  V_DHB_TYPE                   VARCHAR2(2)  := '01';
  V_POSITION_LEVEL             VARCHAR2(3)  := 'ALL';
  V_DBH_OWNER                  VARCHAR2(30) := 'ALL';
  V_DBH_GROUP                  NUMBER;
  V_DBH_SEQ                    NUMBER;
  V_SUMMARY_ID                 NUMBER;
  V_OLA_LEG1                   NUMBER;
  V_OLA_LEG2                   NUMBER;
  V_OLA_LEG3                   NUMBER;
  V_OLA_LEG4                   NUMBER;
  V_COUNT_APP1                 NUMBER;
  V_COUNT_APP2                 NUMBER;
  V_COUNT_APP3                 NUMBER;
  V_COUNT_APP4                 NUMBER;
  V_TOTAL_APP_OUTPUT_CUR_MONTH NUMBER;
  V_WORKING_HR_END_KBANK       VARCHAR2(5);
  V_WORKING_HR_END_PHR         VARCHAR2(5);
  V_WORKING_HR_START_KBANK     VARCHAR2(5);
  V_WORKING_HR_START_PHR       VARCHAR2(5);
  V_APPLICATION_DATE           DATE;
BEGIN
  -- ALL --
  SELECT TRUNC(APP_DATE) + (SYSDATE - TRUNC(SYSDATE))
  INTO V_APPLICATION_DATE
  FROM APPLICATION_DATE;
  SELECT VALUE1
  INTO V_WORKING_HR_END_KBANK
  FROM WORKFLOW_PARAM
  WHERE PARAM_CODE = 'WORKING_HR_END_KBANK';
  SELECT VALUE1
  INTO V_WORKING_HR_END_PHR
  FROM WORKFLOW_PARAM
  WHERE PARAM_CODE = 'WORKING_HR_END_PHR';
  SELECT VALUE1
  INTO V_WORKING_HR_START_KBANK
  FROM WORKFLOW_PARAM
  WHERE PARAM_CODE = 'WORKING_HR_START_KBANK';
  SELECT VALUE1
  INTO V_WORKING_HR_START_PHR
  FROM WORKFLOW_PARAM
  WHERE PARAM_CODE = 'WORKING_HR_START_PHR';
  /* no need to delete data in procedure
  delete from DHB_SUMMARY_DATA where dhb_type = '11';*/
  --%Achievement OLA by leg --
  SELECT /*+INDEX (LT, IDX19_LSW_TASK_TID_U)*/
  COUNT( DISTINCT CASE WHEN OG.JOB_STATE IN ('NM9902','NM9903','NM9904') THEN A.APPLICATION_RECORD_ID ELSE NULL END) TOTAL_APP_OUTPUT_CUR_MONTH,
  COUNT( DISTINCT CASE WHEN OG.JOB_STATE IN ('NM9904') THEN A.APPLICATION_RECORD_ID ELSE NULL END) COUNT_APP3
  INTO V_TOTAL_APP_OUTPUT_CUR_MONTH, V_COUNT_APP3
  FROM DHB_LSW_TASK LT
  JOIN LSW_USR_XREF LU
  ON LU.USER_ID = LT.USER_ID
  JOIN ORIG_APPLICATION_GROUP OG
  ON OG.INSTANT_ID  = LT.BPD_INSTANCE_ID
  --AND OG.JOB_STATE IN ('NM9902','NM9903','NM9904')
  JOIN ORIG_APPLICATION A
  ON A.APPLICATION_GROUP_ID = OG.APPLICATION_GROUP_ID
  JOIN BUSINESS_CLASS B
  ON B.BUS_CLASS_ID                    = A.BUSINESS_CLASS_ID;
  --WHERE LT.STATUS                      = '32'
  --AND TO_CHAR(CLOSE_DATETIME,'YYYYMM') =TO_CHAR(V_APPLICATION_DATE,'YYYYMM') ;

  /*SELECT
  COUNT( DISTINCT A.APPLICATION_RECORD_ID)
  INTO V_COUNT_APP3
  FROM LSW_TASK LT
  JOIN LSW_USR_XREF LU
  ON LU.USER_ID = LT.USER_ID
  JOIN ORIG_APPLICATION_GROUP OG
  ON OG.INSTANT_ID  = LT.BPD_INSTANCE_ID
  AND OG.JOB_STATE IN ('NM9904')
  JOIN ORIG_APPLICATION A
  ON A.APPLICATION_GROUP_ID = OG.APPLICATION_GROUP_ID
  JOIN BUSINESS_CLASS B
  ON B.BUS_CLASS_ID                    = A.BUSINESS_CLASS_ID
  WHERE LT.STATUS                      = '32'
  AND TO_CHAR(CLOSE_DATETIME,'YYYYMM') =TO_CHAR(V_APPLICATION_DATE,'YYYYMM') ;*/

  SELECT /*+INDEX (LT, IDX19_LSW_TASK_TID_U)*/
  COUNT( DISTINCT OG.APPLICATION_GROUP_ID)
  INTO V_OLA_LEG1
  FROM DHB_LSW_TASK LT
  JOIN ORIG_APPLICATION_GROUP OG
  ON OG.INSTANT_ID      = LT.BPD_INSTANCE_ID
  AND OG.JOB_STATE     IN ('NM9902','NM9903','NM9904')
  WHERE ( OG.LEG_TIME1) >
    (SELECT /*+INDEX(M,IDX1_DHB_LEG)*/
    MAX(M.LEG1)
    FROM ORIG_APPLICATION A
    JOIN BUSINESS_CLASS B ON B.BUS_CLASS_ID = A.BUSINESS_CLASS_ID
    JOIN ORIG_LOAN L ON L.APPLICATION_RECORD_ID = A.APPLICATION_RECORD_ID
    JOIN ORIG_CARD C ON C.LOAN_ID = L.LOAN_ID
    JOIN CARD_TYPE CT  ON CT.CARD_TYPE_ID = C.CARD_TYPE
    JOIN DHB_PRECAL_LEG M  ON M.PRODUCT_TYPE  = B.ORG_ID
                                           AND NVL(M.CARD_TYPE,'XXX') = NVL(C.CARD_TYPE,'XXX')
    --JOIN DHB_LEG M ON M.PRODUCT_TYPE                = B.ORG_ID
    --                        AND NVL(M.CARD_LEVEL,CT.CARD_LEVEL)    = CT.CARD_LEVEL
    --                        AND NVL(M.LIST_CARD_TYPE, C.CARD_TYPE) = C.CARD_TYPE
    --                        AND NVL(M.NOT_LIST_CARD_TYPE, 'XXX')  != C.CARD_TYPE
                                          AND M.INCREASE_FLAG    = DECODE(OG.APPLICATION_TYPE,'INC','Y','N')
    AND A.APPLICATION_GROUP_ID             = OG.APPLICATION_GROUP_ID
    )
  --AND LT.STATUS                        = '32'
  --AND TO_CHAR(CLOSE_DATETIME,'YYYYMM') =TO_CHAR(V_APPLICATION_DATE,'YYYYMM') --TO_CHAR(OG.CREATE_DATE,'YYYYMM') =TO_CHAR(V_APPLICATION_DATE,'YYYYMM')
    ;

   --'% OLA Achievement' Leg 2 Move to insert P_GEN_DASHBOARD_08
--  SELECT /*+INDEX (LT, IDX19_LSW_TASK_TID_U)*/
--  COUNT( DISTINCT OG.APPLICATION_GROUP_ID)
--  INTO V_OLA_LEG2
--  FROM DHB_LSW_TASK LT
--  JOIN ORIG_APPLICATION_GROUP OG
--  ON OG.INSTANT_ID      = LT.BPD_INSTANCE_ID
--  AND OG.JOB_STATE     IN ('NM9902','NM9903','NM9904')
--  WHERE ( OG.LEG_TIME2) >
--    (SELECT /*+INDEX(M,IDX1_DHB_LEG)*/
--    MAX(M.LEG2)
--    FROM ORIG_APPLICATION A
--    JOIN BUSINESS_CLASS B
--    ON B.BUS_CLASS_ID = A.BUSINESS_CLASS_ID
--    JOIN ORIG_LOAN L
--    ON L.APPLICATION_RECORD_ID = A.APPLICATION_RECORD_ID
--    JOIN ORIG_CARD C
--    ON C.LOAN_ID = L.LOAN_ID
--    JOIN CARD_TYPE CT
--    ON CT.CARD_TYPE_ID = C.CARD_TYPE
--    JOIN
--
--      DHB_LEG M ON M.PRODUCT_TYPE                = B.ORG_ID
--    AND NVL(M.CARD_LEVEL,CT.CARD_LEVEL)    = CT.CARD_LEVEL
--    AND NVL(M.LIST_CARD_TYPE, C.CARD_TYPE) = C.CARD_TYPE
--    AND NVL(M.NOT_LIST_CARD_TYPE, 'XXX')  != C.CARD_TYPE
--    AND M.INCREASE_FLAG                    = DECODE(OG.APPLICATION_TYPE,'INC','Y','N')
--    AND A.APPLICATION_GROUP_ID             = OG.APPLICATION_GROUP_ID
--    );
--  --AND LT.STATUS                        = '32'
--  --AND TO_CHAR(CLOSE_DATETIME,'YYYYMM') =TO_CHAR(V_APPLICATION_DATE,'YYYYMM') ;

  SELECT /*+INDEX (LT, IDX19_LSW_TASK_TID_U)*/
  COUNT( DISTINCT OG.APPLICATION_GROUP_ID)
  INTO V_OLA_LEG3
  FROM DHB_LSW_TASK LT
  JOIN ORIG_APPLICATION_GROUP OG
  ON OG.INSTANT_ID      = LT.BPD_INSTANCE_ID
  AND OG.JOB_STATE     IN ('NM9904')
  WHERE ( OG.LEG_TIME3) >
    (SELECT /*+INDEX(M,IDX1_DHB_LEG)*/
    MAX(M.LEG3)
    FROM ORIG_APPLICATION A
    JOIN BUSINESS_CLASS B
    ON B.BUS_CLASS_ID = A.BUSINESS_CLASS_ID
    JOIN ORIG_LOAN L
    ON L.APPLICATION_RECORD_ID = A.APPLICATION_RECORD_ID
    JOIN ORIG_CARD C
    ON C.LOAN_ID = L.LOAN_ID
    JOIN CARD_TYPE CT
    ON CT.CARD_TYPE_ID = C.CARD_TYPE
    JOIN DHB_PRECAL_LEG M  ON M.PRODUCT_TYPE  = B.ORG_ID
    AND NVL(M.CARD_TYPE,'XXX') = NVL(C.CARD_TYPE,'XXX')

    --JOIN DHB_LEG M ON M.PRODUCT_TYPE                = B.ORG_ID
    --AND NVL(M.CARD_LEVEL,CT.CARD_LEVEL)    = CT.CARD_LEVEL
    --AND NVL(M.LIST_CARD_TYPE, C.CARD_TYPE) = C.CARD_TYPE
    --AND NVL(M.NOT_LIST_CARD_TYPE, 'XXX')  != C.CARD_TYPE
    AND M.INCREASE_FLAG                    = DECODE(OG.APPLICATION_TYPE,'INC','Y','N')
    AND A.APPLICATION_GROUP_ID             = OG.APPLICATION_GROUP_ID
    );
  --AND LT.STATUS                        = '32'
  --AND TO_CHAR(CLOSE_DATETIME,'YYYYMM') =TO_CHAR(V_APPLICATION_DATE,'YYYYMM') ;

  SELECT /*+INDEX (LT, IDX19_LSW_TASK_TID_U)*/
  COUNT( DISTINCT OG.APPLICATION_GROUP_ID)
  INTO V_OLA_LEG4
  FROM DHB_LSW_TASK LT
  JOIN ORIG_APPLICATION_GROUP OG
  ON OG.INSTANT_ID      = LT.BPD_INSTANCE_ID
  AND OG.JOB_STATE     IN ('NM9904')
  WHERE ( OG.LEG_TIME4) >
    (SELECT /*+INDEX(M,IDX1_DHB_LEG)*/
    MAX(M.LEG4)
    FROM ORIG_APPLICATION A
    JOIN BUSINESS_CLASS B
    ON B.BUS_CLASS_ID = A.BUSINESS_CLASS_ID
    JOIN ORIG_LOAN L
    ON L.APPLICATION_RECORD_ID = A.APPLICATION_RECORD_ID
    JOIN ORIG_CARD C
    ON C.LOAN_ID = L.LOAN_ID
    JOIN CARD_TYPE CT
    ON CT.CARD_TYPE_ID = C.CARD_TYPE
    JOIN DHB_PRECAL_LEG M  ON M.PRODUCT_TYPE  = B.ORG_ID
    AND NVL(M.CARD_TYPE,'XXX') = NVL(C.CARD_TYPE,'XXX')
    --JOIN DHB_LEG M ON M.PRODUCT_TYPE                = B.ORG_ID
    --AND NVL(M.CARD_LEVEL,CT.CARD_LEVEL)    = CT.CARD_LEVEL
    --AND NVL(M.LIST_CARD_TYPE, C.CARD_TYPE) = C.CARD_TYPE
    --AND NVL(M.NOT_LIST_CARD_TYPE, 'XXX')  != C.CARD_TYPE
    AND M.INCREASE_FLAG                    = DECODE(OG.APPLICATION_TYPE,'INC','Y','N')
    AND A.APPLICATION_GROUP_ID             = OG.APPLICATION_GROUP_ID
    );
  --AND LT.STATUS                        = '32'
  --AND TO_CHAR(CLOSE_DATETIME,'YYYYMM') =TO_CHAR(V_APPLICATION_DATE,'YYYYMM') ;

  SELECT DHB_SUMMARY_DATA_PK.NEXTVAL
  INTO V_SUMMARY_ID
  FROM DUAL ;

  V_DHB_TYPE                     := '11';
  V_POSITION_LEVEL               := 'ALL';
  V_DBH_OWNER                    := 'ALL';
  V_DBH_GROUP                    := 1;
  V_DBH_SEQ                      :=1;

  IF V_TOTAL_APP_OUTPUT_CUR_MONTH = 0 THEN
    INSERT
    INTO DHB_SUMMARY_DATA
      (
        SUMMARY_ID,
        POSITION_LEVEL ,
        DHB_TYPE ,
        DHB_OWNER ,
        DHB_GROUP,
        DHP_SEQ,
        VALUE1 ,
        DESCRIPTION1,
        VALUE2,
        DESCRIPTION2 ,
        VALUE3,
        DESCRIPTION3 ,
        VALUE4 ,
        DESCRIPTION4,
        CREATE_DATE,
        STATUS
      )
      VALUES
      (
        V_SUMMARY_ID,
        V_POSITION_LEVEL,
        V_DHB_TYPE,
        V_DBH_OWNER,
        V_DBH_GROUP,
        V_DBH_SEQ,
        0,
        '% OLA Achievement',
        NULL,
        'Leg 1',
        NULL,
        NULL,
        NULL,
        NULL,
        V_APPLICATION_DATE,
        'W'
      );
  ELSE
    INSERT
    INTO DHB_SUMMARY_DATA
      (
        SUMMARY_ID,
        POSITION_LEVEL ,
        DHB_TYPE ,
        DHB_OWNER ,
        DHB_GROUP,
        DHP_SEQ,
        VALUE1 ,
        DESCRIPTION1,
        VALUE2,
        DESCRIPTION2 ,
        VALUE3,
        DESCRIPTION3 ,
        VALUE4 ,
        DESCRIPTION4,
        CREATE_DATE,
        STATUS
      )
      VALUES
      (
        V_SUMMARY_ID,
        V_POSITION_LEVEL,
        V_DHB_TYPE,
        V_DBH_OWNER,
        V_DBH_GROUP,
        V_DBH_SEQ,
        ROUND( ( V_TOTAL_APP_OUTPUT_CUR_MONTH - V_OLA_LEG1) *100/V_TOTAL_APP_OUTPUT_CUR_MONTH,2),
        '% OLA Achievement',
        NULL,
        'Leg 1',
        NULL,
        NULL,
        NULL,
        NULL,
        V_APPLICATION_DATE,
        'W');
  END IF;
  COMMIT;

  --'% OLA Achievement' Leg 2 Move to insert P_GEN_DASHBOARD_08
  /*
  SELECT DHB_SUMMARY_DATA_PK.NEXTVAL
  INTO V_SUMMARY_ID
  FROM DUAL ;

  V_DHB_TYPE                     := '11';
  V_POSITION_LEVEL               := 'ALL';
  V_DBH_OWNER                    := 'ALL';
  V_DBH_GROUP                    := 1;
  V_DBH_SEQ                      :=2;

  IF V_TOTAL_APP_OUTPUT_CUR_MONTH = 0 THEN
    INSERT
    INTO DHB_SUMMARY_DATA
      (
        SUMMARY_ID,
        POSITION_LEVEL ,
        DHB_TYPE ,
        DHB_OWNER ,
        DHB_GROUP,
        DHP_SEQ,
        VALUE1 ,
        DESCRIPTION1,
        VALUE2,
        DESCRIPTION2 ,
        VALUE3,
        DESCRIPTION3 ,
        VALUE4 ,
        DESCRIPTION4,
        CREATE_DATE,
        STATUS
      )
      VALUES
      (
        V_SUMMARY_ID,
        V_POSITION_LEVEL,
        V_DHB_TYPE,
        V_DBH_OWNER,
        V_DBH_GROUP,
        V_DBH_SEQ,
        0,
        '% OLA Achievement',
        NULL,
        'Leg 2',
        NULL,
        NULL,
        NULL,
        NULL,
        V_APPLICATION_DATE,
        'W'
      );
  ELSE
    INSERT
    INTO DHB_SUMMARY_DATA
      (
        SUMMARY_ID,
        POSITION_LEVEL ,
        DHB_TYPE ,
        DHB_OWNER ,
        DHB_GROUP,
        DHP_SEQ,
        VALUE1 ,
        DESCRIPTION1,
        VALUE2,
        DESCRIPTION2 ,
        VALUE3,
        DESCRIPTION3 ,
        VALUE4 ,
        DESCRIPTION4,
        CREATE_DATE,
        STATUS
      )
      VALUES
      (
        V_SUMMARY_ID,
        V_POSITION_LEVEL,
        V_DHB_TYPE,
        V_DBH_OWNER,
        V_DBH_GROUP,
        V_DBH_SEQ,
        ROUND( ( V_TOTAL_APP_OUTPUT_CUR_MONTH - V_OLA_LEG2) *100/V_TOTAL_APP_OUTPUT_CUR_MONTH,2),
        '% OLA Achievement',
        NULL,
        'Leg 2',
        NULL,
        NULL,
        NULL,
        NULL,
        V_APPLICATION_DATE,
        'W');
  END IF;
  COMMIT;
  */

  SELECT DHB_SUMMARY_DATA_PK.NEXTVAL
  INTO V_SUMMARY_ID
  FROM DUAL ;

  V_DHB_TYPE                     := '11';
  V_POSITION_LEVEL               := 'ALL';
  V_DBH_OWNER                    := 'ALL';
  V_DBH_GROUP                    := 1;
  V_DBH_SEQ                      :=3;

  IF V_TOTAL_APP_OUTPUT_CUR_MONTH = 0 THEN
    INSERT
    INTO DHB_SUMMARY_DATA
      (
        SUMMARY_ID,
        POSITION_LEVEL ,
        DHB_TYPE ,
        DHB_OWNER ,
        DHB_GROUP,
        DHP_SEQ,
        VALUE1 ,
        DESCRIPTION1,
        VALUE2,
        DESCRIPTION2 ,
        VALUE3,
        DESCRIPTION3 ,
        VALUE4 ,
        DESCRIPTION4,
        CREATE_DATE,
        STATUS
      )
      VALUES
      (
        V_SUMMARY_ID,
        V_POSITION_LEVEL,
        V_DHB_TYPE,
        V_DBH_OWNER,
        V_DBH_GROUP,
        V_DBH_SEQ,
        0,
        '% OLA Achievement',
        NULL,
        'Leg 3',
        NULL,
        NULL,
        NULL,
        NULL,
        V_APPLICATION_DATE,
        'W'
      );
  ELSE
    INSERT
    INTO DHB_SUMMARY_DATA
      (
        SUMMARY_ID,
        POSITION_LEVEL ,
        DHB_TYPE ,
        DHB_OWNER ,
        DHB_GROUP,
        DHP_SEQ,
        VALUE1 ,
        DESCRIPTION1,
        VALUE2,
        DESCRIPTION2 ,
        VALUE3,
        DESCRIPTION3 ,
        VALUE4 ,
        DESCRIPTION4,
        CREATE_DATE,
        STATUS
      )
      VALUES
      (
        V_SUMMARY_ID,
        V_POSITION_LEVEL,
        V_DHB_TYPE,
        V_DBH_OWNER,
        V_DBH_GROUP,
        V_DBH_SEQ,
        ROUND( ( V_COUNT_APP3 - V_OLA_LEG3) *100/DECODE( V_COUNT_APP3 , 0, 9E99, V_COUNT_APP3),2),
        '% OLA Achievement',
        NULL,
        'Leg 3',
        NULL,
        NULL,
        NULL,
        NULL,
        V_APPLICATION_DATE,
        'W');
  END IF;
  COMMIT;

  SELECT DHB_SUMMARY_DATA_PK.NEXTVAL INTO V_SUMMARY_ID FROM DUAL ;
  V_DHB_TYPE                     := '11';
  V_POSITION_LEVEL               := 'ALL';
  V_DBH_OWNER                    := 'ALL';
  V_DBH_GROUP                    := 1;
  V_DBH_SEQ                      :=4;
  IF V_TOTAL_APP_OUTPUT_CUR_MONTH = 0 THEN
    INSERT
    INTO DHB_SUMMARY_DATA
      (
        SUMMARY_ID,
        POSITION_LEVEL ,
        DHB_TYPE ,
        DHB_OWNER ,
        DHB_GROUP,
        DHP_SEQ,
        VALUE1 ,
        DESCRIPTION1,
        VALUE2,
        DESCRIPTION2 ,
        VALUE3,
        DESCRIPTION3 ,
        VALUE4 ,
        DESCRIPTION4,
        CREATE_DATE,
        STATUS
      )
      VALUES
      (
        V_SUMMARY_ID,
        V_POSITION_LEVEL,
        V_DHB_TYPE,
        V_DBH_OWNER,
        V_DBH_GROUP,
        V_DBH_SEQ,
        0,
        '% OLA Achievement',
        NULL,
        'Leg 4',
        NULL,
        NULL,
        NULL,
        NULL,
        V_APPLICATION_DATE,
        'W'
      );
  ELSE

    INSERT
    INTO DHB_SUMMARY_DATA
      (
        SUMMARY_ID,
        POSITION_LEVEL ,
        DHB_TYPE ,
        DHB_OWNER ,
        DHB_GROUP,
        DHP_SEQ,
        VALUE1 ,
        DESCRIPTION1,
        VALUE2,
        DESCRIPTION2 ,
        VALUE3,
        DESCRIPTION3 ,
        VALUE4 ,
        DESCRIPTION4,
        CREATE_DATE,
        STATUS
      )
      VALUES
      (
        V_SUMMARY_ID,
        V_POSITION_LEVEL,
        V_DHB_TYPE,
        V_DBH_OWNER,
        V_DBH_GROUP,
        V_DBH_SEQ,
        ROUND( ( V_COUNT_APP3 - V_OLA_LEG4) *100/DECODE( V_COUNT_APP3 , 0, 9E99, V_COUNT_APP3),2),
        '% OLA Achievement',
        NULL,
        'Leg 4',
        NULL,
        NULL,
        NULL,
        NULL,
        V_APPLICATION_DATE,
        'W');
  END IF;
  COMMIT;

  INSERT
  INTO DHB_SUMMARY_DATA
    (
      SUMMARY_ID,
      POSITION_LEVEL ,
      DHB_TYPE ,
      DHB_OWNER ,
      DHB_GROUP,
      DHP_SEQ,
      VALUE1 ,
      DESCRIPTION1,
      VALUE2,
      DESCRIPTION2 ,
      VALUE3,
      DESCRIPTION3 ,
      VALUE4 ,
      DESCRIPTION4,
      CREATE_DATE,
      STATUS
    )
  SELECT DHB_SUMMARY_DATA_PK.NEXTVAL,
    POSITION_ID ,
    D.DHB_TYPE ,
    MU.USER_ID ,
    D.DHB_GROUP ,
    D.DHP_SEQ,
    D.VALUE1 ,
    D.DESCRIPTION1,
    D.VALUE2,
    D.DESCRIPTION2 ,
    D.VALUE3,
    D.DESCRIPTION3 ,
    D.VALUE4 ,
    D.DESCRIPTION4,
    D.CREATE_DATE,
    'W'
  FROM DHB_SUMMARY_DATA D
  JOIN
    (SELECT DISTINCT USER_ID ,
      POSITION_ID
    FROM MS_USER_TEAM MU
    WHERE MU.POSITION_ID BETWEEN 1 AND 4
    ) MU
  ON 1           =1
  WHERE DHB_TYPE = '11'
  AND DHB_OWNER  = 'ALL'
  AND STATUS     = 'W';
  COMMIT;
END;

PROCEDURE P_GEN_DASHBOARD_12
AS
  V_DHB_TYPE         VARCHAR2(2)  := '01';
  V_POSITION_LEVEL   VARCHAR2(3)  := 'ALL';
  V_DBH_OWNER        VARCHAR2(30) := 'ALL';
  V_DBH_GROUP        NUMBER;
  V_DBH_SEQ          NUMBER;
  V_SUMMARY_ID       NUMBER;
  V_IN_QUEUE         NUMBER;
  V_INPUT            NUMBER;
  V_ON_HAND          NUMBER;
  V_OUTPUT           NUMBER;
  V_CC_INFINITE      NUMBER;
  V_CC_WISDOM        NUMBER;
  V_CC_PREMIER       NUMBER;
  V_CC_PLATINUM      NUMBER;
  V_CC_GENERIC       NUMBER;
  V_KEC              NUMBER;
  V_KPL              NUMBER;
  V_BUNDLE           NUMBER;
  V_ROLE_NAME        VARCHAR2(100);
  V_APPLICATION_DATE DATE;
  V_ROLE_DISPLAY     VARCHAR2(100);
BEGIN
  -- ALL --
  /* no need to delete data in procedure
  delete from DHB_SUMMARY_DATA where dhb_type = '12';*/
  SELECT TRUNC(APP_DATE) + (SYSDATE - TRUNC(SYSDATE))
  INTO V_APPLICATION_DATE
  FROM APPLICATION_DATE;



    --IA --1--DE1.1-- 2--DE1.2-- 3--DV-- 4--VT-- 5--CA-- 6  --DE2-- 7--FU--8
    FOR V_DBH_SEQ IN 1..8
    LOOP
      CASE (V_DBH_SEQ)
      WHEN 1 THEN
        V_ROLE_NAME    := 'IA';
        V_ROLE_DISPLAY := 'IA';
      WHEN 2 THEN
        V_ROLE_NAME    := 'DE1_1';
        V_ROLE_DISPLAY := 'DE1.1';
      WHEN 3 THEN
        V_ROLE_NAME    := 'DE1_2';
        V_ROLE_DISPLAY := 'DE1.2';
      WHEN 4 THEN
        V_ROLE_NAME    := 'DV';
        V_ROLE_DISPLAY := 'DV';
      WHEN 5 THEN
        V_ROLE_NAME    := 'VT';
        V_ROLE_DISPLAY := 'VT';
      WHEN 6 THEN
        V_ROLE_NAME    := 'CA';
        V_ROLE_DISPLAY := 'CA';
      WHEN 7 THEN
        V_ROLE_NAME    := 'DE2';
        V_ROLE_DISPLAY := 'DE2';
      WHEN 8 THEN
        V_ROLE_NAME    := 'FU';
        V_ROLE_DISPLAY := 'FU';
      END CASE;
      V_DHB_TYPE       := '12';

      --WIP-Product application by station --
      SELECT COUNT( DISTINCT
        CASE
          WHEN LT.STATUS = 12
          AND LT.USER_ID = -1
          THEN NVL(OG.APPLICATION_GROUP_ID,null) --UAT Defect 5214 : count QR
          ELSE NULL
        END) V_IN_QUEUE ---V_IN_QUEUE
        --,COUNT (DISTINCT CASE WHEN TRUNC(READ_DATETIME ) = TRUNC(SYSDATE)  AND LT.USER_ID <> 9 THEN LT.TASK_ID ELSE NULL END)     V_INPUT  --V_INPUT
        ,
        COUNT (DISTINCT
        CASE
          WHEN LT.STATUS  = 12
          AND LT.USER_ID <> -1
          THEN NVL(OG.APPLICATION_GROUP_ID,null) --UAT Defect 5214 : count QR
          ELSE NULL
        END) V_ON_HAND --V_ON_HAND
        --,COUNT (DISTINCT CASE WHEN TRUNC(CLOSE_DATETIME  ) = TRUNC(SYSDATE)  AND LT.STATUS = '32' AND LT.USER_ID <> 9  THEN LT.TASK_ID ELSE NULL END)  V_OUTPUT  --V_OUTPUT
        ,
        COUNT( DISTINCT
        CASE
          WHEN T.JOB_STATE     IS NOT NULL
          AND BUS.COUNT_PRODUCT < 2
          AND B.ORG_ID          = 'CC'
          AND M.CARD_TYPE = '001'
          --AND LCT.PARAM_CODE    = 'CC_INFINITE'
          THEN NVL(A.APPLICATION_RECORD_ID,OG.APPLICATION_GROUP_ID)
          ELSE NULL
        END) INFINITE --INFINITE
        /*01    Platinum ,02    Gold,03    Titanium,04    Classic,05    Privilege*/
        ,
        COUNT( DISTINCT
        CASE
          WHEN T.JOB_STATE     IS NOT NULL
          AND BUS.COUNT_PRODUCT < 2
          AND B.ORG_ID          = 'CC'
          AND M.CARD_TYPE = '002'
          --AND LCT.PARAM_CODE    = 'CC_WISDOM'
          THEN NVL(A.APPLICATION_RECORD_ID,OG.APPLICATION_GROUP_ID)
          ELSE NULL
        END) WISDOM-- WISDOM
        ,
        COUNT( DISTINCT
        CASE
          WHEN T.JOB_STATE     IS NOT NULL
          AND BUS.COUNT_PRODUCT < 2
          AND B.ORG_ID          = 'CC'
          AND M.CARD_TYPE = '003'
          --AND LCT.PARAM_CODE    = 'CC_PREMIER'
          THEN NVL(A.APPLICATION_RECORD_ID,OG.APPLICATION_GROUP_ID)
          ELSE NULL
        END) PREMIER -- PREMIER
        ,
        COUNT( DISTINCT
        CASE
          WHEN T.JOB_STATE        IS NOT NULL
          AND BUS.COUNT_PRODUCT    < 2
          AND B.ORG_ID             = 'CC'
          AND CT.CARD_LEVEL        = '1'
          AND (M.CARD_TYPE IN ('001','002','003')         -- AND (LCT.PARAM_CODE NOT IN ('CC_INFINITE', 'CC_WISDOM','CC_PREMIER' )   OR LCT.PARAM_CODE       IS NULL)
          OR M.CARD_TYPE       IS NULL)
          THEN NVL(A.APPLICATION_RECORD_ID,OG.APPLICATION_GROUP_ID)
          ELSE NULL
        END) PLATINUM-- PLATINUM
        ,
        COUNT( DISTINCT
        CASE
          WHEN T.JOB_STATE            IS NOT NULL
          AND (BUS.COUNT_PRODUCT       < 2
          OR BUS.COUNT_PRODUCT        IS NULL)
          AND (B.ORG_ID                = 'CC'
          OR B.ORG_ID                 IS NULL)
          AND (NVL(CT.CARD_LEVEL,'2') IN ('2','3','4')
          OR CT.CARD_LEVEL            IS NULL)
          THEN NVL(A.APPLICATION_RECORD_ID,OG.APPLICATION_GROUP_ID)
          ELSE NULL
        END) GENERIC --GENERIC
        ,
        COUNT( DISTINCT
        CASE
          WHEN T.JOB_STATE     IS NOT NULL
          AND BUS.COUNT_PRODUCT < 2
          AND B.ORG_ID          = 'KEC'
          THEN NVL(A.APPLICATION_RECORD_ID,OG.APPLICATION_GROUP_ID)
          ELSE NULL
        END) KEC--KEC
        ,
        COUNT( DISTINCT
        CASE
          WHEN T.JOB_STATE     IS NOT NULL
          AND BUS.COUNT_PRODUCT < 2
          AND B.ORG_ID          = 'KPL'
          THEN NVL(A.APPLICATION_RECORD_ID,OG.APPLICATION_GROUP_ID)
          ELSE NULL
        END) KPL--KPL
        ,
        COUNT( DISTINCT
        CASE
          WHEN T.JOB_STATE     IS NOT NULL
          AND BUS.COUNT_PRODUCT > 1
          THEN NVL(A.APPLICATION_RECORD_ID,OG.APPLICATION_GROUP_ID)
          ELSE NULL
        END) BUNDLE-- BUNDLE
      INTO V_IN_QUEUE,
        V_ON_HAND,
        V_CC_INFINITE,
        V_CC_WISDOM,
        V_CC_PREMIER,
        V_CC_PLATINUM,
        V_CC_GENERIC,
        V_KEC,
        V_KPL,
        V_BUNDLE
      FROM ORIG_APPLICATION_GROUP OG
      JOIN
        (SELECT SUBSTR (CSV, INSTR (CSV, ',', 1, LEV) + 1, INSTR (CSV, ',', 1, LEV + 1) - INSTR (CSV, ',', 1, LEV) - 1) JOB_STATE,
          PARAM_CODE
        FROM
          (SELECT ','
            || PARAM_VALUE
            || ',' CSV,
            PARAM_CODE
          FROM GENERAL_PARAM
          WHERE PARAM_CODE LIKE 'WIP_JOBSTATE_%'
          ),
          ( SELECT LEVEL LEV FROM DUAL CONNECT BY LEVEL <= 100
          )
        WHERE LEV <= LENGTH (CSV) - LENGTH (REPLACE (CSV, ',')) - 1
        ) T
      ON T.JOB_STATE   = OG.JOB_STATE
      AND T.PARAM_CODE = 'WIP_JOBSTATE_'
        || V_ROLE_NAME
      JOIN LSW_TASK LT
      ON LT.BPD_INSTANCE_ID = OG.INSTANT_ID
      LEFT JOIN ORIG_APPLICATION A
      ON A.APPLICATION_GROUP_ID = OG.APPLICATION_GROUP_ID
      LEFT JOIN MS_TEMPLATE MT
      ON MT.TEMPLATE_ID = OG.APPLICATION_TEMPLATE
      LEFT JOIN LSW_USR_XREF LU
      ON LU.USER_ID = LT.USER_ID
      LEFT JOIN ORIG_IAS.USER_ROLE UR
      ON UR.USER_NAME = LU.USER_NAME
      LEFT JOIN ORIG_IAS.ROLE R
      ON R.ROLE_ID    = UR.ROLE_ID
      AND R.ROLE_NAME = V_ROLE_NAME
      LEFT JOIN BUSINESS_CLASS B
      ON B.BUS_CLASS_ID = A.BUSINESS_CLASS_ID
      LEFT JOIN ORIG_LOAN L
      ON L.APPLICATION_RECORD_ID = A.APPLICATION_RECORD_ID
      LEFT JOIN ORIG_CARD C
      ON C.LOAN_ID = L.LOAN_ID
      LEFT JOIN CARD_TYPE CT
      ON CT.CARD_TYPE_ID = C.CARD_TYPE
      LEFT JOIN DHB_PRECAL_LEG M
      ON M.PRODUCT_TYPE  = B.ORG_ID
      AND NVL(M.CARD_TYPE,'XXX') = NVL(C.CARD_TYPE,'XXX')
      AND M.INCREASE_FLAG  = DECODE(OG.APPLICATION_TYPE,'INC','Y','N')
      /* LEFT JOIN

        (SELECT SUBSTR (CSV, INSTR (CSV, ',', 1, LEV) + 1, INSTR (CSV, ',', 1, LEV + 1) - INSTR (CSV, ',', 1, LEV) - 1) LIST_CARD_TYPE,
          PARAM_CODE
        FROM
          (SELECT ','
            || PARAM_VALUE
            || ',' CSV,
            PARAM_CODE
          FROM GENERAL_PARAM
          WHERE PARAM_CODE IN ('CC_INFINITE','CC_PREMIER','CC_WISDOM')
          ),
          ( SELECT LEVEL LEV FROM DUAL CONNECT BY LEVEL <= 100
          )
        WHERE LEV <= LENGTH (CSV) - LENGTH (REPLACE (CSV, ',')) - 1
        ) LCT
      ON LCT.LIST_CARD_TYPE = C.CARD_TYPE
      LEFT JOIN
        (SELECT SUBSTR (CSV, INSTR (CSV, ',', 1, LEV) + 1, INSTR (CSV, ',', 1, LEV + 1) - INSTR (CSV, ',', 1, LEV) - 1) LIST_CARD_TYPE,
          PARAM_CODE
        FROM
          (SELECT ','
            || PARAM_VALUE
            || ',' CSV,
            PARAM_CODE
          FROM GENERAL_PARAM
          WHERE PARAM_CODE IN ('CC_INFINITE','CC_PREMIER','CC_WISDOM')
          ),
          ( SELECT LEVEL LEV FROM DUAL CONNECT BY LEVEL <= 100
          )
        WHERE LEV <= LENGTH (CSV) - LENGTH (REPLACE (CSV, ',')) - 1
        ) LCX
      ON CT.CARD_LEVEL       = '1'
      AND LCX.LIST_CARD_TYPE = C.CARD_TYPE
      */
      LEFT JOIN
        (SELECT COUNT(DISTINCT B.ORG_ID) COUNT_PRODUCT,
          APPLICATION_GROUP_ID
        FROM ORIG_APPLICATION APP,
          BUSINESS_CLASS B
        WHERE APP.BUSINESS_CLASS_ID = B.BUS_CLASS_ID
        GROUP BY APPLICATION_GROUP_ID
        ) BUS
      ON BUS.APPLICATION_GROUP_ID = OG.APPLICATION_GROUP_ID;

      SELECT COUNT(DISTINCT NVL(OG.APPLICATION_GROUP_ID,null)) --UAT Defect 5214 : count QR
      INTO V_INPUT
      FROM ORIG_APPLICATION_GROUP OG
      JOIN LSW_TASK LT
      ON LT.BPD_INSTANCE_ID = OG.INSTANT_ID
      LEFT JOIN ORIG_APPLICATION A
      ON A.APPLICATION_GROUP_ID = OG.APPLICATION_GROUP_ID
      JOIN LSW_USR_XREF LU
      ON LU.USER_ID = LT.USER_ID
      JOIN ORIG_IAS.USER_ROLE UR
      ON UR.USER_NAME = LU.USER_NAME
      JOIN ORIG_IAS.ROLE R
      ON R.ROLE_ID                = UR.ROLE_ID
      AND R.ROLE_NAME             = V_ROLE_NAME
      WHERE TRUNC(READ_DATETIME ) = TRUNC(SYSDATE)
      AND LT.STATUS               = '32'
      AND LT.USER_ID             <> 9;

      SELECT COUNT(DISTINCT NVL(OG.APPLICATION_GROUP_ID,null)) --UAT Defect 5214 : count QR
      INTO V_OUTPUT
      FROM ORIG_APPLICATION_GROUP OG
      JOIN LSW_TASK LT
      ON LT.BPD_INSTANCE_ID = OG.INSTANT_ID
      LEFT JOIN ORIG_APPLICATION A
      ON A.APPLICATION_GROUP_ID = OG.APPLICATION_GROUP_ID
      JOIN LSW_USR_XREF LU
      ON LU.USER_ID = LT.USER_ID
      JOIN ORIG_IAS.USER_ROLE UR
      ON UR.USER_NAME = LU.USER_NAME
      JOIN ORIG_IAS.ROLE R
      ON R.ROLE_ID                 = UR.ROLE_ID
      AND R.ROLE_NAME              = V_ROLE_NAME
      WHERE TRUNC(CLOSE_DATETIME ) = TRUNC(SYSDATE)
      AND LT.STATUS                = '32'
      AND LT.USER_ID              <> 9;



        FOR CUR IN
  (SELECT DISTINCT USER_ID,
    (POSITION_ID) POSITION_ID
  FROM MS_USER_TEAM
  WHERE POSITION_ID IN (1,2)
  ) LOOP

      V_POSITION_LEVEL := CUR.POSITION_ID;
      V_DBH_OWNER      := CUR.USER_ID;

      SELECT DHB_SUMMARY_DATA_PK.NEXTVAL INTO V_SUMMARY_ID FROM DUAL ;
      V_DBH_GROUP := 1;
      INSERT
      INTO DHB_SUMMARY_DATA
        (
          SUMMARY_ID,
          POSITION_LEVEL ,
          DHB_TYPE ,
          DHB_OWNER ,
          DHB_GROUP,
          DHP_SEQ,
          VALUE1 ,
          DESCRIPTION1,
          VALUE2,
          DESCRIPTION2 ,
          VALUE3,
          DESCRIPTION3 ,
          VALUE4 ,
          DESCRIPTION4,
          CREATE_DATE,
          STATUS
        )
        VALUES
        (
          V_SUMMARY_ID,
          V_POSITION_LEVEL,
          V_DHB_TYPE,
          V_DBH_OWNER,
          V_DBH_GROUP,
          V_DBH_SEQ,
          V_IN_QUEUE,
          V_ROLE_DISPLAY,
          NULL,
          'In Queue',
          NULL,
          1,
          NULL,
          NULL,
          V_APPLICATION_DATE,
          'W'
        );

      COMMIT;

      SELECT DHB_SUMMARY_DATA_PK.NEXTVAL
      INTO V_SUMMARY_ID
      FROM DUAL ;

      V_DBH_GROUP := 2;

      INSERT
      INTO DHB_SUMMARY_DATA
        (
          SUMMARY_ID,
          POSITION_LEVEL ,
          DHB_TYPE ,
          DHB_OWNER ,
          DHB_GROUP,
          DHP_SEQ,
          VALUE1 ,
          DESCRIPTION1,
          VALUE2,
          DESCRIPTION2 ,
          VALUE3,
          DESCRIPTION3 ,
          VALUE4 ,
          DESCRIPTION4,
          CREATE_DATE,
          STATUS
        )
        VALUES
        (
          V_SUMMARY_ID,
          V_POSITION_LEVEL,
          V_DHB_TYPE,
          V_DBH_OWNER,
          V_DBH_GROUP,
          V_DBH_SEQ,
          V_INPUT,
          V_ROLE_DISPLAY,
          NULL,
          'Input',
          NULL,
          2,
          NULL,
          NULL,
          V_APPLICATION_DATE,
          'W'
        );
      COMMIT;

      SELECT DHB_SUMMARY_DATA_PK.NEXTVAL
      INTO V_SUMMARY_ID
      FROM DUAL ;

      V_DBH_GROUP := 3;

      INSERT
      INTO DHB_SUMMARY_DATA
        (
          SUMMARY_ID,
          POSITION_LEVEL ,
          DHB_TYPE ,
          DHB_OWNER ,
          DHB_GROUP,
          DHP_SEQ,
          VALUE1 ,
          DESCRIPTION1,
          VALUE2,
          DESCRIPTION2 ,
          VALUE3,
          DESCRIPTION3 ,
          VALUE4 ,
          DESCRIPTION4,
          CREATE_DATE,
          STATUS
        )
        VALUES
        (
          V_SUMMARY_ID,
          V_POSITION_LEVEL,
          V_DHB_TYPE,
          V_DBH_OWNER,
          V_DBH_GROUP,
          V_DBH_SEQ,
          V_ON_HAND,
          V_ROLE_DISPLAY,
          NULL,
          'On Hand',
          NULL,
          3,
          NULL,
          NULL,
          V_APPLICATION_DATE,
          'W'
        );
      COMMIT;

      SELECT DHB_SUMMARY_DATA_PK.NEXTVAL
      INTO V_SUMMARY_ID
      FROM DUAL ;

      V_DBH_GROUP := 4;

      INSERT
      INTO DHB_SUMMARY_DATA
        (
          SUMMARY_ID,
          POSITION_LEVEL ,
          DHB_TYPE ,
          DHB_OWNER ,
          DHB_GROUP,
          DHP_SEQ,
          VALUE1 ,
          DESCRIPTION1,
          VALUE2,
          DESCRIPTION2 ,
          VALUE3,
          DESCRIPTION3 ,
          VALUE4 ,
          DESCRIPTION4,
          CREATE_DATE,
          STATUS
        )
        VALUES
        (
          V_SUMMARY_ID,
          V_POSITION_LEVEL,
          V_DHB_TYPE,
          V_DBH_OWNER,
          V_DBH_GROUP,
          V_DBH_SEQ,
          V_OUTPUT,
          V_ROLE_DISPLAY,
          NULL,
          'Output',
          NULL,
          4,
          NULL,
          NULL,
          V_APPLICATION_DATE,
          'W'
        );
      COMMIT;

      SELECT DHB_SUMMARY_DATA_PK.NEXTVAL
      INTO V_SUMMARY_ID
      FROM DUAL ;

      V_DBH_GROUP := 5;

      INSERT
      INTO DHB_SUMMARY_DATA
        (
          SUMMARY_ID,
          POSITION_LEVEL ,
          DHB_TYPE ,
          DHB_OWNER ,
          DHB_GROUP,
          DHP_SEQ,
          VALUE1 ,
          DESCRIPTION1,
          VALUE2,
          DESCRIPTION2 ,
          VALUE3,
          DESCRIPTION3 ,
          VALUE4 ,
          DESCRIPTION4,
          CREATE_DATE,
          STATUS
        )
        VALUES
        (
          V_SUMMARY_ID,
          V_POSITION_LEVEL,
          V_DHB_TYPE,
          V_DBH_OWNER,
          V_DBH_GROUP,
          V_DBH_SEQ,
          V_CC_INFINITE,
          V_ROLE_DISPLAY,
          NULL,
          'WIP Infinite',
          NULL,
          5,
          NULL,
          NULL,
          V_APPLICATION_DATE,
          'W'
        );
      COMMIT;

      SELECT DHB_SUMMARY_DATA_PK.NEXTVAL
      INTO V_SUMMARY_ID
      FROM DUAL ;

      V_DBH_GROUP := 6;

      INSERT
      INTO DHB_SUMMARY_DATA
        (
          SUMMARY_ID,
          POSITION_LEVEL ,
          DHB_TYPE ,
          DHB_OWNER ,
          DHB_GROUP,
          DHP_SEQ,
          VALUE1 ,
          DESCRIPTION1,
          VALUE2,
          DESCRIPTION2 ,
          VALUE3,
          DESCRIPTION3 ,
          VALUE4 ,
          DESCRIPTION4,
          CREATE_DATE,
          STATUS
        )
        VALUES
        (
          V_SUMMARY_ID,
          V_POSITION_LEVEL,
          V_DHB_TYPE,
          V_DBH_OWNER,
          V_DBH_GROUP,
          V_DBH_SEQ,
          V_CC_WISDOM,
          V_ROLE_DISPLAY,
          NULL,
          'WIP Wisdom',
          NULL,
          5,
          NULL,
          NULL,
          V_APPLICATION_DATE,
          'W'
        );
      COMMIT;

      SELECT DHB_SUMMARY_DATA_PK.NEXTVAL
      INTO V_SUMMARY_ID
      FROM DUAL ;

      V_DBH_GROUP := 7;

      INSERT
      INTO DHB_SUMMARY_DATA
        (
          SUMMARY_ID,
          POSITION_LEVEL ,
          DHB_TYPE ,
          DHB_OWNER ,
          DHB_GROUP,
          DHP_SEQ,
          VALUE1 ,
          DESCRIPTION1,
          VALUE2,
          DESCRIPTION2 ,
          VALUE3,
          DESCRIPTION3 ,
          VALUE4 ,
          DESCRIPTION4,
          CREATE_DATE,
          STATUS
        )
        VALUES
        (
          V_SUMMARY_ID,
          V_POSITION_LEVEL,
          V_DHB_TYPE,
          V_DBH_OWNER,
          V_DBH_GROUP,
          V_DBH_SEQ,
          V_CC_PREMIER,
          V_ROLE_DISPLAY,
          NULL,
          'WIP Premier',
          NULL,
          5,
          NULL,
          NULL,
          V_APPLICATION_DATE,
          'W'
        );
      COMMIT;

      SELECT DHB_SUMMARY_DATA_PK.NEXTVAL INTO V_SUMMARY_ID FROM DUAL ;

      V_DBH_GROUP := 8;

      INSERT
      INTO DHB_SUMMARY_DATA
        (
          SUMMARY_ID,
          POSITION_LEVEL ,
          DHB_TYPE ,
          DHB_OWNER ,
          DHB_GROUP,
          DHP_SEQ,
          VALUE1 ,
          DESCRIPTION1,
          VALUE2,
          DESCRIPTION2 ,
          VALUE3,
          DESCRIPTION3 ,
          VALUE4 ,
          DESCRIPTION4,
          CREATE_DATE,
          STATUS
        )
        VALUES
        (
          V_SUMMARY_ID,
          V_POSITION_LEVEL,
          V_DHB_TYPE,
          V_DBH_OWNER,
          V_DBH_GROUP,
          V_DBH_SEQ,
          V_CC_PLATINUM,
          V_ROLE_DISPLAY,
          NULL,
          'WIP Platinum',
          NULL,
          5,
          NULL,
          NULL,
          V_APPLICATION_DATE,
          'W'
        );
      COMMIT;

      SELECT DHB_SUMMARY_DATA_PK.NEXTVAL
      INTO V_SUMMARY_ID
      FROM DUAL ;

      V_DBH_GROUP := 9;

      INSERT
      INTO DHB_SUMMARY_DATA
        (
          SUMMARY_ID,
          POSITION_LEVEL ,
          DHB_TYPE ,
          DHB_OWNER ,
          DHB_GROUP,
          DHP_SEQ,
          VALUE1 ,
          DESCRIPTION1,
          VALUE2,
          DESCRIPTION2 ,
          VALUE3,
          DESCRIPTION3 ,
          VALUE4 ,
          DESCRIPTION4,
          CREATE_DATE,
          STATUS
        )
        VALUES
        (
          V_SUMMARY_ID,
          V_POSITION_LEVEL,
          V_DHB_TYPE,
          V_DBH_OWNER,
          V_DBH_GROUP,
          V_DBH_SEQ,
          V_CC_GENERIC,
          V_ROLE_DISPLAY,
          NULL,
          'WIP Generic',
          NULL,
          5,
          NULL,
          NULL,
          V_APPLICATION_DATE,
          'W'
        );
      COMMIT;

      SELECT DHB_SUMMARY_DATA_PK.NEXTVAL
      INTO V_SUMMARY_ID
      FROM DUAL ;

      V_DBH_GROUP := 10;

      INSERT
      INTO DHB_SUMMARY_DATA
        (
          SUMMARY_ID,
          POSITION_LEVEL ,
          DHB_TYPE ,
          DHB_OWNER ,
          DHB_GROUP,
          DHP_SEQ,
          VALUE1 ,
          DESCRIPTION1,
          VALUE2,
          DESCRIPTION2 ,
          VALUE3,
          DESCRIPTION3 ,
          VALUE4 ,
          DESCRIPTION4,
          CREATE_DATE,
          STATUS
        )
        VALUES
        (
          V_SUMMARY_ID,
          V_POSITION_LEVEL,
          V_DHB_TYPE,
          V_DBH_OWNER,
          V_DBH_GROUP,
          V_DBH_SEQ,
          V_KEC,
          V_ROLE_DISPLAY,
          NULL,
          'WIP KEC',
          NULL,
          5,
          NULL,
          NULL,
          V_APPLICATION_DATE,
          'W'
        );
      COMMIT;

      SELECT DHB_SUMMARY_DATA_PK.NEXTVAL
      INTO V_SUMMARY_ID
      FROM DUAL ;

      V_DBH_GROUP := 11;

      INSERT
      INTO DHB_SUMMARY_DATA
        (
          SUMMARY_ID,
          POSITION_LEVEL ,
          DHB_TYPE ,
          DHB_OWNER ,
          DHB_GROUP,
          DHP_SEQ,
          VALUE1 ,
          DESCRIPTION1,
          VALUE2,
          DESCRIPTION2 ,
          VALUE3,
          DESCRIPTION3 ,
          VALUE4 ,
          DESCRIPTION4,
          CREATE_DATE,
          STATUS
        )
        VALUES
        (
          V_SUMMARY_ID,
          V_POSITION_LEVEL,
          V_DHB_TYPE,
          V_DBH_OWNER,
          V_DBH_GROUP,
          V_DBH_SEQ,
          V_KPL,
          V_ROLE_DISPLAY,
          NULL,
          'WIP KPL',
          NULL,
          5,
          NULL,
          NULL,
          V_APPLICATION_DATE,
          'W'
        );
      COMMIT;

      SELECT DHB_SUMMARY_DATA_PK.NEXTVAL
      INTO V_SUMMARY_ID
      FROM DUAL ;

      V_DBH_GROUP := 12;

      INSERT
      INTO DHB_SUMMARY_DATA
        (
          SUMMARY_ID,
          POSITION_LEVEL ,
          DHB_TYPE ,
          DHB_OWNER ,
          DHB_GROUP,
          DHP_SEQ,
          VALUE1 ,
          DESCRIPTION1,
          VALUE2,
          DESCRIPTION2 ,
          VALUE3,
          DESCRIPTION3 ,
          VALUE4 ,
          DESCRIPTION4,
          CREATE_DATE,
          STATUS
        )
        VALUES
        (
          V_SUMMARY_ID,
          V_POSITION_LEVEL,
          V_DHB_TYPE,
          V_DBH_OWNER,
          V_DBH_GROUP,
          V_DBH_SEQ,
          V_BUNDLE,
          V_ROLE_DISPLAY,
          NULL,
          'WIP Bundle',
          NULL,
          5,
          NULL,
          NULL,
          V_APPLICATION_DATE,
          'W'
        );
      COMMIT;
    END LOOP;
  END LOOP;
END;

procedure p_exec_main as
    v_error_msg varchar2(500);
    v_s_time    timestamp;
    v_dhb_type  varchar2(2);
    v_interval  number:= 12;
    v_reset_interval number;
    v_end_date  date := sysdate;
    v_start_date    date;
    v_status    varchar2(20) := 'complete';
    v_diff      number := 0;
    v_start_diff    number;
    v_update_by  varchar2(20) := 'system';
    v_schedule_id number;
    v_cnt number := 0;
    v_application_date date;
    v_enable_flag  varchar2(1);

begin

    begin

        select trunc(app_date) + (sysdate - trunc(sysdate))
        into v_application_date
        from application_date;

         select dhb_schedule_id_seq.nextval into v_schedule_id
         from dual ;

         select count(1) into v_cnt
         from dhb_schedule;

         if v_cnt = 0 then
            insert into dhb_schedule
             (schedule_id
             ,create_date
             ,update_date)
            values
             (v_schedule_id
              ,v_application_date
              ,v_application_date
              );
         else

             update dhb_schedule
             set schedule_id = v_schedule_id
                ,create_date = v_application_date
                ,update_date = v_application_date;

         end if;


    for i in 0..12 loop

        v_dhb_type := substr('0'||i,-2,2);

        select interval, end_date, status, enable_flag, reset_interval, start_date
        into  v_interval, v_end_date, v_status , v_enable_flag, v_reset_interval, v_start_date
        from dhb_config
        where dhb_type = v_dhb_type;

     if v_enable_flag = 'Y' then
        v_diff := (sysdate - NVL(v_end_date, (SYSDATE-1)))*(24*60);
        v_start_diff := (sysdate - NVL(v_start_date, (SYSDATE-1)))*(24*60);

        if (v_start_diff > v_reset_interval or ( v_status = 'complete' and v_diff > v_interval)) then

            v_s_time := sysdate;
            update dhb_config
            set start_date = v_s_time,
                end_date = null,
                used_time = '',
                status = 'processing',
                update_date = sysdate,
                update_by = v_update_by
            where dhb_type = v_dhb_type;
            commit;

            if v_dhb_type = '00' then

                p_gen_staff_perf_dashboard;
                delete from dhb_staff_performance dp where dp.status = 'A';
                update dhb_staff_performance dp set dp.status = 'A';

            elsif v_dhb_type = '01' then

                p_gen_dhb_team_day;  --01
                p_gen_dhb_team_week; --01
                p_gen_dashboard_01;

            elsif v_dhb_type = '02' then
                --Incoming Apps of DHB_02 reuse data from DHB_09
                P_GEN_DHB_LSW_TASK;
                P_GEN_DHB_JOB_STATE;
                p_gen_dashboard_09;
                p_gen_dhb_team;--02
                p_gen_dashboard_02;
            elsif v_dhb_type = '03' then

                p_gen_dhb_team_hour; --03
                p_gen_dashboard_03;

            elsif v_dhb_type = '04' then
                P_GEN_DHB_LSW_TASK;
                --p_gen_dhb_team_month;--04
                DELETE FROM DHB_COMPARISON_DATA; COMMIT;
                P_GEN_DHB_COMPARISON_DATA;
                p_gen_dashboard_04;

            elsif v_dhb_type = '05' then
                P_GEN_DHB_LSW_TASK;
                P_GEN_DHB_JOB_STATE;
                p_gen_dashboard_05_06;

            elsif v_dhb_type = '07' then
                p_gen_dhb_role;--07
                p_gen_dashboard_07;

            elsif v_dhb_type = '08' then
                P_GEN_DHB_LSW_TASK;
                P_GEN_DHB_JOB_STATE;
                P_GEN_PRECAL_LEG;
                --P_GEN_DHB_LEG;
                p_gen_dashboard_08;

            elsif v_dhb_type = '09' then
                --P_GEN_DHB_LSW_TASK;
                --P_GEN_DHB_JOB_STATE;
                --P_GEN_PRECAL_LEG
                --P_GEN_DHB_LEG;
                --p_gen_dashboard_09;
                null;--for data consistency sake, move step to DHB_02
            elsif v_dhb_type = '10' then
                P_GEN_DHB_LSW_TASK;
                P_GEN_DHB_JOB_STATE;
                P_GEN_PRECAL_LEG;
                --P_GEN_DHB_LEG;
                p_gen_dashboard_10;

            elsif v_dhb_type = '11' then
                P_GEN_DHB_LSW_TASK;
                P_GEN_DHB_JOB_STATE;
                P_GEN_PRECAL_LEG;
                --P_GEN_DHB_LEG;--11
                p_gen_dashboard_11;

            elsif v_dhb_type = '12' then
                P_GEN_DHB_LSW_TASK;
                P_GEN_DHB_JOB_STATE;
                P_GEN_PRECAL_LEG;
                --P_GEN_DHB_LEG;
                p_gen_dashboard_12;

            end if;

            update dhb_config
            set end_date = sysdate,
                used_time = (sysdate - v_s_time),
                --used_time = ((sysdate - v_s_time)*(24*60*60)),
                status = 'complete',
                update_date = sysdate,
                update_by = v_update_by
            where dhb_type = v_dhb_type;
            commit;
            dbms_output.put_line(v_dhb_type||' elapsed time : '||(sysdate - v_s_time));

            if v_dhb_type = '05' then

                    delete from dhb_summary_data ds
                    where ds.dhb_type  in ('05','06')
                    and ds.status = 'A';
        --            and trunc(ds.create_date) < (select nvl((select max(trunc(sysdate-num_day))
        --                                                    from dhb_config
        --                                                    where dhb_type = v_dhb_type)
        --                                              ,trunc(sysdate)-365)
        --                                          from dual);
            elsif v_dhb_type <> '06' then

                    delete from dhb_summary_data ds
                    where ds.dhb_type = v_dhb_type
                    and ds.status = 'A';

            end if;


            if v_dhb_type = '05' then

                  update dhb_summary_data
                   set status = 'A'
                  where dhb_type in ('05','06')
                  and status = 'W' ;

            elsif v_dhb_type <> '06' then

                  update dhb_summary_data
                    set status = 'A'
                  where dhb_type = v_dhb_type
                  and status = 'W' ;

            end if;

        end if; -- if v_status = 'complete' and v_diff > v_interval then
     end if;
      --if v_enable_flag = 'Y' then
    end loop;

    exception when others then
        v_error_msg := sqlerrm;
        rollback;
        delete from dhb_summary_data ds where ds.status = 'W';
        delete from dhb_staff_performance dp where dp.status = 'W';
        insert into dhb_error_log(dhb_type,err_desc,err_date)values('ALL',v_error_msg, sysdate);
        commit;
    end;
    dbms_output.put_line('Delete old Active Data(Status = A) and Enable new data used time : ' || (sysdate - v_s_time));
    DBMS_OUTPUT.PUT_LINE('Execute GEN_DASHBOARD_DATA [Result : SUCCESS]');
exception when others then
    v_error_msg := sqlerrm;
    delete from dhb_summary_data ds where ds.status = 'W';
    delete from dhb_staff_performance dp where dp.status = 'W';
    insert into dhb_error_log(dhb_type,err_desc,err_date)values('ALL',v_error_msg, sysdate);
    commit;
    DBMS_OUTPUT.PUT_LINE('Error while execution dashboard data, detail : '||v_error_msg);
    DBMS_OUTPUT.PUT_LINE('Execute GEN_DASHBOARD_DATA [Result : FAIL]');
end;

PROCEDURE p_clone_task as
    v_error_msg varchar2(500);
    v_s_time    timestamp;
    v_dhb_type  varchar2(2);
    v_interval  number:= 12;
    v_reset_interval number;
    v_end_date  date := sysdate;
    v_start_date    date;
    v_status    varchar2(20) := 'complete';
    v_diff      number := 0;
    v_start_diff    number;
    v_update_by  varchar2(20) := 'system';
    v_schedule_id number;
    v_cnt number := 0;
    v_application_date date;
    v_enable_flag  varchar2(1);

begin

delete DHB_BP_LSW_TASK
where
EXISTS (SELECT 1 FROM LSW_TASK WHERE DHB_BP_LSW_TASK.TASK_ID = LSW_TASK.TASK_ID and LSW_TASK.ACTIVITY_TASK_TYPE=1);

insert
into
DHB_BP_LSW_TASK
(
TASK_ID
,TIP
,SNAPSHOT_ID
,USER_ID
,GROUP_ID
,PARTICIPANT_ID
,MANAGERS_GROUP_ID
,EXPERT_PARTICIPANT_REF
,STATUS
,PRIORITY_ID
,DUE_DATE
,DUE_TIME
,AT_RISK_DATE
,SUBJECT
,RCVD_DATETIME
,RCVD_FROM
,RCVD_TASK_ID
,COLLABORATION
,SENT_DATETIME
,READ_DATETIME
,CLOSE_DATETIME
,CLOSE_BY
,ORIG_TASK_ID
,START_PROCESS_REF
,CACHED_PROCESS_VERSION_ID
,GROUP_ID_TYPE
,EXECUTION_STATUS
,BPD_INSTANCE_ID
,CREATED_BY_BPD_REF
,CACHED_CBB_VERSION_ID
,CREATED_BY_BPD_FLOW_OBJECT_ID
,ASSUMER_ID
,ATTACHED_FORM_REF
,CACHED_FORM_VERSION_ID
,ATTACHED_EXT_ACTIVITY_REF
,CACHED_EXTACT_VERSION_ID
,SHAREPOINT_DISCUSSION_URL
,ACTIVITY_NAME
,ACTIVITY_TASK_TYPE
)
select
TASK_ID
,TIP
,SNAPSHOT_ID
,USER_ID
,GROUP_ID
,PARTICIPANT_ID
,MANAGERS_GROUP_ID
,EXPERT_PARTICIPANT_REF
,STATUS
,PRIORITY_ID
,DUE_DATE
,DUE_TIME
,AT_RISK_DATE
,SUBJECT
,RCVD_DATETIME
,RCVD_FROM
,RCVD_TASK_ID
,COLLABORATION
,SENT_DATETIME
,READ_DATETIME
,CLOSE_DATETIME
,CLOSE_BY
,ORIG_TASK_ID
,START_PROCESS_REF
,CACHED_PROCESS_VERSION_ID
,GROUP_ID_TYPE
,EXECUTION_STATUS
,BPD_INSTANCE_ID
,CREATED_BY_BPD_REF
,CACHED_CBB_VERSION_ID
,CREATED_BY_BPD_FLOW_OBJECT_ID
,ASSUMER_ID
,ATTACHED_FORM_REF
,CACHED_FORM_VERSION_ID
,ATTACHED_EXT_ACTIVITY_REF
,CACHED_EXTACT_VERSION_ID
,SHAREPOINT_DISCUSSION_URL
,ACTIVITY_NAME
,ACTIVITY_TASK_TYPE
from LSW_TASK
where NOT EXISTS (SELECT 1 FROM ORIG_APP.DHB_BP_LSW_TASK WHERE DHB_BP_LSW_TASK.TASK_ID = LSW_TASK.TASK_ID)  and LSW_TASK.ACTIVITY_TASK_TYPE=1;

end;

end;

/

spool off