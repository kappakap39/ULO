#!/bin/sh
##############################################################################################################
##	config.sh 
##	written by : AVALANT                               	           
##	reviewed by :  
##	Version : 0.0.1 
##	Usage :
##############################################################################################################
##############################################################################################################
##	Specify initial Path variable
##############################################################################################################
JAVA_HOME=$(grep -e ^JAVA_HOME= ./batch_config.properties | cut -d= -f2)
JAVA_OPTION=$(grep -e ^JAVA_OPTION= ./batch_config.properties | cut -d= -f2-9)
INF_BATCH_CORE_PATH=$(grep -e ^INF_BATCH_CORE_PATH= ./batch_config.properties | cut -d= -f2)
INF_BATCH_LIB_PATH=$(grep -e ^INF_BATCH_LIB_PATH= ./batch_config.properties | cut -d= -f2)
BATCH_ID="CARD_NOTIFICATION"

echo "-------------------------------------------------------------------------------------------------------"
echo "BATCH_ID : $BATCH_ID"
echo "-------------------------------------------------------------------------------------------------------"
echo "JAVA_HOME : $JAVA_HOME"
echo "JAVA_OPTION : $JAVA_OPTION"
echo "INF_BATCH_CORE_PATH : $INF_BATCH_CORE_PATH"
echo "INF_BATCH_LIB_PATH : $INF_BATCH_LIB_PATH"
echo "-------------------------------------------------------------------------------------------------------"

##############################################################################################################
##	Exec Command
##############################################################################################################
COMMAND="$JAVA_HOME $JAVA_OPTION -jar $INF_BATCH_CORE_PATH $BATCH_ID $INF_BATCH_LIB_PATH"

echo "-------------------------------------------------------------------------------------------------------"
echo "COMMAND : $COMMAND"
echo "-------------------------------------------------------------------------------------------------------"

$COMMAND

if [ $? != 0 ]
then
	exit 1
fi
exit 0
