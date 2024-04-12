package com.kasikornbank.ava.iibservice;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;
import decisionservice_iib.ApplicationGroupDataM;

public class IIBServiceHttpPortProxy{

    protected Descriptor _descriptor;

    public class Descriptor {
        private com.kasikornbank.ava.iibservice.IIBServiceHttpService _service = null;
        private com.kasikornbank.ava.iibservice.IIBService _proxy = null;
        private Dispatch<Source> _dispatch = null;
        private boolean _useJNDIOnly = false;

        public Descriptor() {
            init();
        }

        public Descriptor(URL wsdlLocation, QName serviceName) {
            _service = new com.kasikornbank.ava.iibservice.IIBServiceHttpService(wsdlLocation, serviceName);
            initCommon();
        }

        public void init() {
            _service = null;
            _proxy = null;
            _dispatch = null;
            try
            {
                InitialContext ctx = new InitialContext();
                _service = (com.kasikornbank.ava.iibservice.IIBServiceHttpService)ctx.lookup("java:comp/env/service/IIBServiceHttpService");
            }
            catch (NamingException e)
            {
                if ("true".equalsIgnoreCase(System.getProperty("DEBUG_PROXY"))) {
                    System.out.println("JNDI lookup failure: javax.naming.NamingException: " + e.getMessage());
                    e.printStackTrace(System.out);
                }
            }

            if (_service == null && !_useJNDIOnly)
                _service = new com.kasikornbank.ava.iibservice.IIBServiceHttpService();
            initCommon();
        }

        private void initCommon() {
            _proxy = _service.getIIBServiceHttpPort();
        }

        public com.kasikornbank.ava.iibservice.IIBService getProxy() {
            return _proxy;
        }

        public void useJNDIOnly(boolean useJNDIOnly) {
            _useJNDIOnly = useJNDIOnly;
            init();
        }

        public Dispatch<Source> getDispatch() {
            if (_dispatch == null ) {
                QName portQName = new QName("http://ava.kasikornbank.com/IIBService", "IIBServiceHttpPort");
                _dispatch = _service.createDispatch(portQName, Source.class, Service.Mode.MESSAGE);

                String proxyEndpointUrl = getEndpoint();
                BindingProvider bp = (BindingProvider) _dispatch;
                String dispatchEndpointUrl = (String) bp.getRequestContext().get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY);
                if (!dispatchEndpointUrl.equals(proxyEndpointUrl))
                    bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, proxyEndpointUrl);
            }
            return _dispatch;
        }

        public String getEndpoint() {
            BindingProvider bp = (BindingProvider) _proxy;
            return (String) bp.getRequestContext().get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY);
        }

        public void setEndpoint(String endpointUrl) {
            BindingProvider bp = (BindingProvider) _proxy;
            bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointUrl);

            if (_dispatch != null ) {
                bp = (BindingProvider) _dispatch;
                bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointUrl);
            }
        }

        public void setMTOMEnabled(boolean enable) {
            SOAPBinding binding = (SOAPBinding) ((BindingProvider) _proxy).getBinding();
            binding.setMTOMEnabled(enable);
        }
    }

    public IIBServiceHttpPortProxy() {
        _descriptor = new Descriptor();
        _descriptor.setMTOMEnabled(false);
    }

    public IIBServiceHttpPortProxy(URL wsdlLocation, QName serviceName) {
        _descriptor = new Descriptor(wsdlLocation, serviceName);
        _descriptor.setMTOMEnabled(false);
    }

    public Descriptor _getDescriptor() {
        return _descriptor;
    }

    public ApplicationGroupDataM cis0368I01(ApplicationGroupDataM cis0368I01Request) {
        return _getDescriptor().getProxy().cis0368I01(cis0368I01Request);
    }

    public ApplicationGroupDataM cvrs1312I01(ApplicationGroupDataM cvrs1312I01Request) {
        return _getDescriptor().getProxy().cvrs1312I01(cvrs1312I01Request);
    }

    public ApplicationGroupDataM kbank1211I01(ApplicationGroupDataM kbank1211I01Request) {
        return _getDescriptor().getProxy().kbank1211I01(kbank1211I01Request);
    }

    public ApplicationGroupDataM kbank1550I01(ApplicationGroupDataM kbank1550I01Request) {
        return _getDescriptor().getProxy().kbank1550I01(kbank1550I01Request);
    }

    public ApplicationGroupDataM fraud(ApplicationGroupDataM fraudRequest) {
        return _getDescriptor().getProxy().fraud(fraudRequest);
    }

    public ApplicationGroupDataM bol(ApplicationGroupDataM bolRequest) {
        return _getDescriptor().getProxy().bol(bolRequest);
    }

    public ApplicationGroupDataM cardLink(ApplicationGroupDataM cardLinkRequest) {
        return _getDescriptor().getProxy().cardLink(cardLinkRequest);
    }

    public ApplicationGroupDataM hris(ApplicationGroupDataM hrisRequest) {
        return _getDescriptor().getProxy().hris(hrisRequest);
    }

    public ApplicationGroupDataM koc(ApplicationGroupDataM kocRequest) {
        return _getDescriptor().getProxy().koc(kocRequest);
    }

    public ApplicationGroupDataM lpm(ApplicationGroupDataM lpmRequest) {
        return _getDescriptor().getProxy().lpm(lpmRequest);
    }

    public ApplicationGroupDataM payroll(ApplicationGroupDataM payrollRequest) {
        return _getDescriptor().getProxy().payroll(payrollRequest);
    }

    public ApplicationGroupDataM safe(ApplicationGroupDataM safeRequest) {
        return _getDescriptor().getProxy().safe(safeRequest);
    }

    public ApplicationGroupDataM tcbLoan(ApplicationGroupDataM tcbLoanRequest) {
        return _getDescriptor().getProxy().tcbLoan(tcbLoanRequest);
    }

    public ApplicationGroupDataM wfInquire(ApplicationGroupDataM wfInquireRequest) {
        return _getDescriptor().getProxy().wfInquire(wfInquireRequest);
    }

    public ApplicationGroupDataM kcbs(ApplicationGroupDataM kcbsRequest) {
        return _getDescriptor().getProxy().kcbs(kcbsRequest);
    }

    public ApplicationGroupDataM kAsset(ApplicationGroupDataM kAssetRequest) {
        return _getDescriptor().getProxy().kAsset(kAssetRequest);
    }

    public ApplicationGroupDataM bScore(ApplicationGroupDataM bScoreRequest) {
        return _getDescriptor().getProxy().bScore(bScoreRequest);
    }

    public ApplicationGroupDataM savingAccount(ApplicationGroupDataM savingAccountRequest) {
        return _getDescriptor().getProxy().savingAccount(savingAccountRequest);
    }

    public ApplicationGroupDataM fixAccount(ApplicationGroupDataM fixAccountRequest) {
        return _getDescriptor().getProxy().fixAccount(fixAccountRequest);
    }

    public ApplicationGroupDataM currentAccount(ApplicationGroupDataM currentAccountRequest) {
        return _getDescriptor().getProxy().currentAccount(currentAccountRequest);
    }

    public ApplicationGroupDataM wealth(ApplicationGroupDataM wealthRequest) {
        return _getDescriptor().getProxy().wealth(wealthRequest);
    }

    public ApplicationGroupDataM capPort(ApplicationGroupDataM capPortRequest) {
        return _getDescriptor().getProxy().capPort(capPortRequest);
    }

}