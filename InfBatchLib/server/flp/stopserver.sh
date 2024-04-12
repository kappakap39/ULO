#!/bin/sh
##############################################################################################################
##	stopserver.sh 
##	written by : AVALANT                               	           
##	reviewed by :  
##	Version : 0.0.1 
##	Usage :
##############################################################################################################
/wasprofile/FLPULOAppSrv01/bin/stopServer.sh DMServer -username wasadmin -password passw0rd
/wasprofile/FLPULOAppSrv01/bin/stopServer.sh IasServer01 -username wasadmin -password passw0rd
/wasprofile/FLPULOAppSrv01/bin/stopServer.sh ORIGMonitorServer -username wasadmin -password passw0rd
/wasprofile/FLPULOAppSrv01/bin/stopServer.sh ORIGServiceServer -username wasadmin -password passw0rd
/wasprofile/FLPULOAppSrv01/bin/stopServer.sh OrigServer01 -username wasadmin -password passw0rd
/wasprofile/FLPULOAppSrv01/bin/stopNode.sh -username wasadmin -password passw0rd
/wasprofile/FLPULODmgr01/bin/stopManager.sh -username wasadmin -password passw0rd
