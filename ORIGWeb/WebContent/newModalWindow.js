var winModalWindow 
function IgnoreEvents(e){
  return false;
}
 
function ShowWindow(url, varWidth, varHeight){
  if (window.showModalDialog) {
    window.showModalDialog(url, window, "dialogWidth="+varWidth+"px;dialogHeight="+varHeight+"px;status=no;help=no;");
  }else{
    window.top.captureEvents (Event.CLICK|Event.FOCUS);
    window.top.onclick=IgnoreEvents;
    window.top.onfocus=HandleFocus ;
    winModalWindow = window.open(url, "ModalChild", "dependent=yes,width="+varWidth+",height="+varHeight);
    winModalWindow.focus();
  }
} 
function HandleFocus(){
  if (winModalWindow){
    if (!winModalWindow.closed){
      winModalWindow.focus();
    }else{
      window.top.releaseEvents (Event.CLICK|Event.FOCUS);
    }
  }
  return false;
}
