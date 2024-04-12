#!/bin/sh
##############################################################################################################
##	stopserver.sh 
##	written by : AVALANT                               	           
##	reviewed by :  
##	Version : 0.0.1 
##	Usage :
##############################################################################################################
/wasprofile/FLPBpmSrv01/bin/stopServer.sh appsvr01 -username bpm_admin -password passw0rd
/wasprofile/FLPBpmSrv01/bin/stopServer.sh messvr01 -username bpm_admin -password passw0rd
/wasprofile/FLPBpmSrv01/bin/stopServer.sh supsvr01 -username bpm_admin -password passw0rd
/wasprofile/FLPBpmSrv01/bin/stopNode.sh -username bpm_admin -password passw0rd
/wasprofile/FLPDmgrSrv01/bin/stopManager.sh -username bpm_admin -password passw0rd