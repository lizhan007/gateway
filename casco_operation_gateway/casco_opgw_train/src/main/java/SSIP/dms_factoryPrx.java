// **********************************************************************
//
// Copyright (c) 2003-2013 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************
//
// Ice version 3.5.1
//
// <auto-generated>
//
// Generated from file `ssip_service.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package SSIP;

public interface dms_factoryPrx extends Ice.ObjectPrx
{
    public boolean isMainServer(String server);

    public boolean isMainServer(String server, java.util.Map<String, String> __ctx);

    public Ice.AsyncResult begin_isMainServer(String server);

    public Ice.AsyncResult begin_isMainServer(String server, java.util.Map<String, String> __ctx);

    public Ice.AsyncResult begin_isMainServer(String server, Ice.Callback __cb);

    public Ice.AsyncResult begin_isMainServer(String server, java.util.Map<String, String> __ctx, Ice.Callback __cb);

    public Ice.AsyncResult begin_isMainServer(String server, Callback_dms_factory_isMainServer __cb);

    public Ice.AsyncResult begin_isMainServer(String server, java.util.Map<String, String> __ctx, Callback_dms_factory_isMainServer __cb);

    public boolean end_isMainServer(Ice.AsyncResult __result);

    public DACPrx getDac();

    public DACPrx getDac(java.util.Map<String, String> __ctx);

    public Ice.AsyncResult begin_getDac();

    public Ice.AsyncResult begin_getDac(java.util.Map<String, String> __ctx);

    public Ice.AsyncResult begin_getDac(Ice.Callback __cb);

    public Ice.AsyncResult begin_getDac(java.util.Map<String, String> __ctx, Ice.Callback __cb);

    public Ice.AsyncResult begin_getDac(Callback_dms_factory_getDac __cb);

    public Ice.AsyncResult begin_getDac(java.util.Map<String, String> __ctx, Callback_dms_factory_getDac __cb);

    public DACPrx end_getDac(Ice.AsyncResult __result);

    public DAQPrx getDaq();

    public DAQPrx getDaq(java.util.Map<String, String> __ctx);

    public Ice.AsyncResult begin_getDaq();

    public Ice.AsyncResult begin_getDaq(java.util.Map<String, String> __ctx);

    public Ice.AsyncResult begin_getDaq(Ice.Callback __cb);

    public Ice.AsyncResult begin_getDaq(java.util.Map<String, String> __ctx, Ice.Callback __cb);

    public Ice.AsyncResult begin_getDaq(Callback_dms_factory_getDaq __cb);

    public Ice.AsyncResult begin_getDaq(java.util.Map<String, String> __ctx, Callback_dms_factory_getDaq __cb);

    public DAQPrx end_getDaq(Ice.AsyncResult __result);

    public COMCTRLPrx getComctrl();

    public COMCTRLPrx getComctrl(java.util.Map<String, String> __ctx);

    public Ice.AsyncResult begin_getComctrl();

    public Ice.AsyncResult begin_getComctrl(java.util.Map<String, String> __ctx);

    public Ice.AsyncResult begin_getComctrl(Ice.Callback __cb);

    public Ice.AsyncResult begin_getComctrl(java.util.Map<String, String> __ctx, Ice.Callback __cb);

    public Ice.AsyncResult begin_getComctrl(Callback_dms_factory_getComctrl __cb);

    public Ice.AsyncResult begin_getComctrl(java.util.Map<String, String> __ctx, Callback_dms_factory_getComctrl __cb);

    public COMCTRLPrx end_getComctrl(Ice.AsyncResult __result);

    public SSIP.NEWS.dms_observerPrx getObserver();

    public SSIP.NEWS.dms_observerPrx getObserver(java.util.Map<String, String> __ctx);

    public Ice.AsyncResult begin_getObserver();

    public Ice.AsyncResult begin_getObserver(java.util.Map<String, String> __ctx);

    public Ice.AsyncResult begin_getObserver(Ice.Callback __cb);

    public Ice.AsyncResult begin_getObserver(java.util.Map<String, String> __ctx, Ice.Callback __cb);

    public Ice.AsyncResult begin_getObserver(Callback_dms_factory_getObserver __cb);

    public Ice.AsyncResult begin_getObserver(java.util.Map<String, String> __ctx, Callback_dms_factory_getObserver __cb);

    public SSIP.NEWS.dms_observerPrx end_getObserver(Ice.AsyncResult __result);

    public SSIP.NEWS.dms_publisherPrx getPubliser();

    public SSIP.NEWS.dms_publisherPrx getPubliser(java.util.Map<String, String> __ctx);

    public Ice.AsyncResult begin_getPubliser();

    public Ice.AsyncResult begin_getPubliser(java.util.Map<String, String> __ctx);

    public Ice.AsyncResult begin_getPubliser(Ice.Callback __cb);

    public Ice.AsyncResult begin_getPubliser(java.util.Map<String, String> __ctx, Ice.Callback __cb);

    public Ice.AsyncResult begin_getPubliser(Callback_dms_factory_getPubliser __cb);

    public Ice.AsyncResult begin_getPubliser(java.util.Map<String, String> __ctx, Callback_dms_factory_getPubliser __cb);

    public SSIP.NEWS.dms_publisherPrx end_getPubliser(Ice.AsyncResult __result);
}
