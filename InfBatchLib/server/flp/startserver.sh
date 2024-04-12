#!/bin/sh
##############################################################################################################
##	startserver.sh 
##	written by : AVALANT                               	           
##	reviewed by :  
##	Version : 0.0.1 
##	Usage :
##############################################################################################################
/wasprofile/FLPULODmgr01/bin/startManager.sh
/wasprofile/FLPULOAppSrv01/bin/startNode.sh
/wasprofile/FLPULOAppSrv01/bin/startServer.sh ORIGServiceServer
/wasprofile/FLPULOAppSrv01/bin/startServer.sh DMServer
/wasprofile/FLPULOAppSrv01/bin/startServer.sh IasServer01
/wasprofile/FLPULOAppSrv01/bin/startServer.sh ORIGMonitorServer
/wasprofile/FLPULOAppSrv01/bin/startServer.sh OrigServer01
