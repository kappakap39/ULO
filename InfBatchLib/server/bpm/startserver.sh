#!/bin/sh
##############################################################################################################
##	startserver.sh 
##	written by : AVALANT                               	           
##	reviewed by :  
##	Version : 0.0.1 
##	Usage :
##############################################################################################################
/wasprofile/FLPDmgrSrv01/bin/startManager.sh
/wasprofile/FLPBpmSrv01/bin/startNode.sh
/wasprofile/FLPBpmSrv01/bin/startServer.sh supsvr01
/wasprofile/FLPBpmSrv01/bin/startServer.sh messvr01
/wasprofile/FLPBpmSrv01/bin/startServer.sh appsvr01