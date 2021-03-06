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

public final class dms_factoryPrxHelper extends Ice.ObjectPrxHelperBase implements dms_factoryPrx
{
    private static final String __getComctrl_name = "getComctrl";

    public COMCTRLPrx getComctrl()
    {
        return getComctrl(null, false);
    }

    public COMCTRLPrx getComctrl(java.util.Map<String, String> __ctx)
    {
        return getComctrl(__ctx, true);
    }

    private COMCTRLPrx getComctrl(java.util.Map<String, String> __ctx, boolean __explicitCtx)
    {
        if(__explicitCtx && __ctx == null)
        {
            __ctx = _emptyContext;
        }
        final Ice.Instrumentation.InvocationObserver __observer = IceInternal.ObserverHelper.get(this, "getComctrl", __ctx);
        int __cnt = 0;
        try
        {
            while(true)
            {
                Ice._ObjectDel __delBase = null;
                try
                {
                    __checkTwowayOnly("getComctrl");
                    __delBase = __getDelegate(false);
                    _dms_factoryDel __del = (_dms_factoryDel)__delBase;
                    return __del.getComctrl(__ctx, __observer);
                }
                catch(IceInternal.LocalExceptionWrapper __ex)
                {
                    __cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt, __observer);
                }
                catch(Ice.LocalException __ex)
                {
                    __cnt = __handleException(__delBase, __ex, null, __cnt, __observer);
                }
            }
        }
        finally
        {
            if(__observer != null)
            {
                __observer.detach();
            }
        }
    }

    public Ice.AsyncResult begin_getComctrl()
    {
        return begin_getComctrl(null, false, null);
    }

    public Ice.AsyncResult begin_getComctrl(java.util.Map<String, String> __ctx)
    {
        return begin_getComctrl(__ctx, true, null);
    }

    public Ice.AsyncResult begin_getComctrl(Ice.Callback __cb)
    {
        return begin_getComctrl(null, false, __cb);
    }

    public Ice.AsyncResult begin_getComctrl(java.util.Map<String, String> __ctx, Ice.Callback __cb)
    {
        return begin_getComctrl(__ctx, true, __cb);
    }

    public Ice.AsyncResult begin_getComctrl(Callback_dms_factory_getComctrl __cb)
    {
        return begin_getComctrl(null, false, __cb);
    }

    public Ice.AsyncResult begin_getComctrl(java.util.Map<String, String> __ctx, Callback_dms_factory_getComctrl __cb)
    {
        return begin_getComctrl(__ctx, true, __cb);
    }

    private Ice.AsyncResult begin_getComctrl(java.util.Map<String, String> __ctx, boolean __explicitCtx, IceInternal.CallbackBase __cb)
    {
        __checkAsyncTwowayOnly(__getComctrl_name);
        IceInternal.OutgoingAsync __result = new IceInternal.OutgoingAsync(this, __getComctrl_name, __cb);
        try
        {
            __result.__prepare(__getComctrl_name, Ice.OperationMode.Idempotent, __ctx, __explicitCtx);
            __result.__writeEmptyParams();
            __result.__send(true);
        }
        catch(Ice.LocalException __ex)
        {
            __result.__exceptionAsync(__ex);
        }
        return __result;
    }

    public COMCTRLPrx end_getComctrl(Ice.AsyncResult __result)
    {
        Ice.AsyncResult.__check(__result, this, __getComctrl_name);
        boolean __ok = __result.__wait();
        try
        {
            if(!__ok)
            {
                try
                {
                    __result.__throwUserException();
                }
                catch(Ice.UserException __ex)
                {
                    throw new Ice.UnknownUserException(__ex.ice_name(), __ex);
                }
            }
            IceInternal.BasicStream __is = __result.__startReadParams();
            COMCTRLPrx __ret;
            __ret = COMCTRLPrxHelper.__read(__is);
            __result.__endReadParams();
            return __ret;
        }
        catch(Ice.LocalException ex)
        {
            Ice.Instrumentation.InvocationObserver __obsv = __result.__getObserver();
            if(__obsv != null)
            {
                __obsv.failed(ex.ice_name());
            }
            throw ex;
        }
    }

    private static final String __getDac_name = "getDac";

    public DACPrx getDac()
    {
        return getDac(null, false);
    }

    public DACPrx getDac(java.util.Map<String, String> __ctx)
    {
        return getDac(__ctx, true);
    }

    private DACPrx getDac(java.util.Map<String, String> __ctx, boolean __explicitCtx)
    {
        if(__explicitCtx && __ctx == null)
        {
            __ctx = _emptyContext;
        }
        final Ice.Instrumentation.InvocationObserver __observer = IceInternal.ObserverHelper.get(this, "getDac", __ctx);
        int __cnt = 0;
        try
        {
            while(true)
            {
                Ice._ObjectDel __delBase = null;
                try
                {
                    __checkTwowayOnly("getDac");
                    __delBase = __getDelegate(false);
                    _dms_factoryDel __del = (_dms_factoryDel)__delBase;
                    return __del.getDac(__ctx, __observer);
                }
                catch(IceInternal.LocalExceptionWrapper __ex)
                {
                    __cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt, __observer);
                }
                catch(Ice.LocalException __ex)
                {
                    __cnt = __handleException(__delBase, __ex, null, __cnt, __observer);
                }
            }
        }
        finally
        {
            if(__observer != null)
            {
                __observer.detach();
            }
        }
    }

    public Ice.AsyncResult begin_getDac()
    {
        return begin_getDac(null, false, null);
    }

    public Ice.AsyncResult begin_getDac(java.util.Map<String, String> __ctx)
    {
        return begin_getDac(__ctx, true, null);
    }

    public Ice.AsyncResult begin_getDac(Ice.Callback __cb)
    {
        return begin_getDac(null, false, __cb);
    }

    public Ice.AsyncResult begin_getDac(java.util.Map<String, String> __ctx, Ice.Callback __cb)
    {
        return begin_getDac(__ctx, true, __cb);
    }

    public Ice.AsyncResult begin_getDac(Callback_dms_factory_getDac __cb)
    {
        return begin_getDac(null, false, __cb);
    }

    public Ice.AsyncResult begin_getDac(java.util.Map<String, String> __ctx, Callback_dms_factory_getDac __cb)
    {
        return begin_getDac(__ctx, true, __cb);
    }

    private Ice.AsyncResult begin_getDac(java.util.Map<String, String> __ctx, boolean __explicitCtx, IceInternal.CallbackBase __cb)
    {
        __checkAsyncTwowayOnly(__getDac_name);
        IceInternal.OutgoingAsync __result = new IceInternal.OutgoingAsync(this, __getDac_name, __cb);
        try
        {
            __result.__prepare(__getDac_name, Ice.OperationMode.Idempotent, __ctx, __explicitCtx);
            __result.__writeEmptyParams();
            __result.__send(true);
        }
        catch(Ice.LocalException __ex)
        {
            __result.__exceptionAsync(__ex);
        }
        return __result;
    }

    public DACPrx end_getDac(Ice.AsyncResult __result)
    {
        Ice.AsyncResult.__check(__result, this, __getDac_name);
        boolean __ok = __result.__wait();
        try
        {
            if(!__ok)
            {
                try
                {
                    __result.__throwUserException();
                }
                catch(Ice.UserException __ex)
                {
                    throw new Ice.UnknownUserException(__ex.ice_name(), __ex);
                }
            }
            IceInternal.BasicStream __is = __result.__startReadParams();
            DACPrx __ret;
            __ret = DACPrxHelper.__read(__is);
            __result.__endReadParams();
            return __ret;
        }
        catch(Ice.LocalException ex)
        {
            Ice.Instrumentation.InvocationObserver __obsv = __result.__getObserver();
            if(__obsv != null)
            {
                __obsv.failed(ex.ice_name());
            }
            throw ex;
        }
    }

    private static final String __getDaq_name = "getDaq";

    public DAQPrx getDaq()
    {
        return getDaq(null, false);
    }

    public DAQPrx getDaq(java.util.Map<String, String> __ctx)
    {
        return getDaq(__ctx, true);
    }

    private DAQPrx getDaq(java.util.Map<String, String> __ctx, boolean __explicitCtx)
    {
        if(__explicitCtx && __ctx == null)
        {
            __ctx = _emptyContext;
        }
        final Ice.Instrumentation.InvocationObserver __observer = IceInternal.ObserverHelper.get(this, "getDaq", __ctx);
        int __cnt = 0;
        try
        {
            while(true)
            {
                Ice._ObjectDel __delBase = null;
                try
                {
                    __checkTwowayOnly("getDaq");
                    __delBase = __getDelegate(false);
                    _dms_factoryDel __del = (_dms_factoryDel)__delBase;
                    return __del.getDaq(__ctx, __observer);
                }
                catch(IceInternal.LocalExceptionWrapper __ex)
                {
                    __cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt, __observer);
                }
                catch(Ice.LocalException __ex)
                {
                    __cnt = __handleException(__delBase, __ex, null, __cnt, __observer);
                }
            }
        }
        finally
        {
            if(__observer != null)
            {
                __observer.detach();
            }
        }
    }

    public Ice.AsyncResult begin_getDaq()
    {
        return begin_getDaq(null, false, null);
    }

    public Ice.AsyncResult begin_getDaq(java.util.Map<String, String> __ctx)
    {
        return begin_getDaq(__ctx, true, null);
    }

    public Ice.AsyncResult begin_getDaq(Ice.Callback __cb)
    {
        return begin_getDaq(null, false, __cb);
    }

    public Ice.AsyncResult begin_getDaq(java.util.Map<String, String> __ctx, Ice.Callback __cb)
    {
        return begin_getDaq(__ctx, true, __cb);
    }

    public Ice.AsyncResult begin_getDaq(Callback_dms_factory_getDaq __cb)
    {
        return begin_getDaq(null, false, __cb);
    }

    public Ice.AsyncResult begin_getDaq(java.util.Map<String, String> __ctx, Callback_dms_factory_getDaq __cb)
    {
        return begin_getDaq(__ctx, true, __cb);
    }

    private Ice.AsyncResult begin_getDaq(java.util.Map<String, String> __ctx, boolean __explicitCtx, IceInternal.CallbackBase __cb)
    {
        __checkAsyncTwowayOnly(__getDaq_name);
        IceInternal.OutgoingAsync __result = new IceInternal.OutgoingAsync(this, __getDaq_name, __cb);
        try
        {
            __result.__prepare(__getDaq_name, Ice.OperationMode.Idempotent, __ctx, __explicitCtx);
            __result.__writeEmptyParams();
            __result.__send(true);
        }
        catch(Ice.LocalException __ex)
        {
            __result.__exceptionAsync(__ex);
        }
        return __result;
    }

    public DAQPrx end_getDaq(Ice.AsyncResult __result)
    {
        Ice.AsyncResult.__check(__result, this, __getDaq_name);
        boolean __ok = __result.__wait();
        try
        {
            if(!__ok)
            {
                try
                {
                    __result.__throwUserException();
                }
                catch(Ice.UserException __ex)
                {
                    throw new Ice.UnknownUserException(__ex.ice_name(), __ex);
                }
            }
            IceInternal.BasicStream __is = __result.__startReadParams();
            DAQPrx __ret;
            __ret = DAQPrxHelper.__read(__is);
            __result.__endReadParams();
            return __ret;
        }
        catch(Ice.LocalException ex)
        {
            Ice.Instrumentation.InvocationObserver __obsv = __result.__getObserver();
            if(__obsv != null)
            {
                __obsv.failed(ex.ice_name());
            }
            throw ex;
        }
    }

    private static final String __getObserver_name = "getObserver";

    public SSIP.NEWS.dms_observerPrx getObserver()
    {
        return getObserver(null, false);
    }

    public SSIP.NEWS.dms_observerPrx getObserver(java.util.Map<String, String> __ctx)
    {
        return getObserver(__ctx, true);
    }

    private SSIP.NEWS.dms_observerPrx getObserver(java.util.Map<String, String> __ctx, boolean __explicitCtx)
    {
        if(__explicitCtx && __ctx == null)
        {
            __ctx = _emptyContext;
        }
        final Ice.Instrumentation.InvocationObserver __observer = IceInternal.ObserverHelper.get(this, "getObserver", __ctx);
        int __cnt = 0;
        try
        {
            while(true)
            {
                Ice._ObjectDel __delBase = null;
                try
                {
                    __checkTwowayOnly("getObserver");
                    __delBase = __getDelegate(false);
                    _dms_factoryDel __del = (_dms_factoryDel)__delBase;
                    return __del.getObserver(__ctx, __observer);
                }
                catch(IceInternal.LocalExceptionWrapper __ex)
                {
                    __cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt, __observer);
                }
                catch(Ice.LocalException __ex)
                {
                    __cnt = __handleException(__delBase, __ex, null, __cnt, __observer);
                }
            }
        }
        finally
        {
            if(__observer != null)
            {
                __observer.detach();
            }
        }
    }

    public Ice.AsyncResult begin_getObserver()
    {
        return begin_getObserver(null, false, null);
    }

    public Ice.AsyncResult begin_getObserver(java.util.Map<String, String> __ctx)
    {
        return begin_getObserver(__ctx, true, null);
    }

    public Ice.AsyncResult begin_getObserver(Ice.Callback __cb)
    {
        return begin_getObserver(null, false, __cb);
    }

    public Ice.AsyncResult begin_getObserver(java.util.Map<String, String> __ctx, Ice.Callback __cb)
    {
        return begin_getObserver(__ctx, true, __cb);
    }

    public Ice.AsyncResult begin_getObserver(Callback_dms_factory_getObserver __cb)
    {
        return begin_getObserver(null, false, __cb);
    }

    public Ice.AsyncResult begin_getObserver(java.util.Map<String, String> __ctx, Callback_dms_factory_getObserver __cb)
    {
        return begin_getObserver(__ctx, true, __cb);
    }

    private Ice.AsyncResult begin_getObserver(java.util.Map<String, String> __ctx, boolean __explicitCtx, IceInternal.CallbackBase __cb)
    {
        __checkAsyncTwowayOnly(__getObserver_name);
        IceInternal.OutgoingAsync __result = new IceInternal.OutgoingAsync(this, __getObserver_name, __cb);
        try
        {
            __result.__prepare(__getObserver_name, Ice.OperationMode.Idempotent, __ctx, __explicitCtx);
            __result.__writeEmptyParams();
            __result.__send(true);
        }
        catch(Ice.LocalException __ex)
        {
            __result.__exceptionAsync(__ex);
        }
        return __result;
    }

    public SSIP.NEWS.dms_observerPrx end_getObserver(Ice.AsyncResult __result)
    {
        Ice.AsyncResult.__check(__result, this, __getObserver_name);
        boolean __ok = __result.__wait();
        try
        {
            if(!__ok)
            {
                try
                {
                    __result.__throwUserException();
                }
                catch(Ice.UserException __ex)
                {
                    throw new Ice.UnknownUserException(__ex.ice_name(), __ex);
                }
            }
            IceInternal.BasicStream __is = __result.__startReadParams();
            SSIP.NEWS.dms_observerPrx __ret;
            __ret = SSIP.NEWS.dms_observerPrxHelper.__read(__is);
            __result.__endReadParams();
            return __ret;
        }
        catch(Ice.LocalException ex)
        {
            Ice.Instrumentation.InvocationObserver __obsv = __result.__getObserver();
            if(__obsv != null)
            {
                __obsv.failed(ex.ice_name());
            }
            throw ex;
        }
    }

    private static final String __getPubliser_name = "getPubliser";

    public SSIP.NEWS.dms_publisherPrx getPubliser()
    {
        return getPubliser(null, false);
    }

    public SSIP.NEWS.dms_publisherPrx getPubliser(java.util.Map<String, String> __ctx)
    {
        return getPubliser(__ctx, true);
    }

    private SSIP.NEWS.dms_publisherPrx getPubliser(java.util.Map<String, String> __ctx, boolean __explicitCtx)
    {
        if(__explicitCtx && __ctx == null)
        {
            __ctx = _emptyContext;
        }
        final Ice.Instrumentation.InvocationObserver __observer = IceInternal.ObserverHelper.get(this, "getPubliser", __ctx);
        int __cnt = 0;
        try
        {
            while(true)
            {
                Ice._ObjectDel __delBase = null;
                try
                {
                    __checkTwowayOnly("getPubliser");
                    __delBase = __getDelegate(false);
                    _dms_factoryDel __del = (_dms_factoryDel)__delBase;
                    return __del.getPubliser(__ctx, __observer);
                }
                catch(IceInternal.LocalExceptionWrapper __ex)
                {
                    __cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt, __observer);
                }
                catch(Ice.LocalException __ex)
                {
                    __cnt = __handleException(__delBase, __ex, null, __cnt, __observer);
                }
            }
        }
        finally
        {
            if(__observer != null)
            {
                __observer.detach();
            }
        }
    }

    public Ice.AsyncResult begin_getPubliser()
    {
        return begin_getPubliser(null, false, null);
    }

    public Ice.AsyncResult begin_getPubliser(java.util.Map<String, String> __ctx)
    {
        return begin_getPubliser(__ctx, true, null);
    }

    public Ice.AsyncResult begin_getPubliser(Ice.Callback __cb)
    {
        return begin_getPubliser(null, false, __cb);
    }

    public Ice.AsyncResult begin_getPubliser(java.util.Map<String, String> __ctx, Ice.Callback __cb)
    {
        return begin_getPubliser(__ctx, true, __cb);
    }

    public Ice.AsyncResult begin_getPubliser(Callback_dms_factory_getPubliser __cb)
    {
        return begin_getPubliser(null, false, __cb);
    }

    public Ice.AsyncResult begin_getPubliser(java.util.Map<String, String> __ctx, Callback_dms_factory_getPubliser __cb)
    {
        return begin_getPubliser(__ctx, true, __cb);
    }

    private Ice.AsyncResult begin_getPubliser(java.util.Map<String, String> __ctx, boolean __explicitCtx, IceInternal.CallbackBase __cb)
    {
        __checkAsyncTwowayOnly(__getPubliser_name);
        IceInternal.OutgoingAsync __result = new IceInternal.OutgoingAsync(this, __getPubliser_name, __cb);
        try
        {
            __result.__prepare(__getPubliser_name, Ice.OperationMode.Idempotent, __ctx, __explicitCtx);
            __result.__writeEmptyParams();
            __result.__send(true);
        }
        catch(Ice.LocalException __ex)
        {
            __result.__exceptionAsync(__ex);
        }
        return __result;
    }

    public SSIP.NEWS.dms_publisherPrx end_getPubliser(Ice.AsyncResult __result)
    {
        Ice.AsyncResult.__check(__result, this, __getPubliser_name);
        boolean __ok = __result.__wait();
        try
        {
            if(!__ok)
            {
                try
                {
                    __result.__throwUserException();
                }
                catch(Ice.UserException __ex)
                {
                    throw new Ice.UnknownUserException(__ex.ice_name(), __ex);
                }
            }
            IceInternal.BasicStream __is = __result.__startReadParams();
            SSIP.NEWS.dms_publisherPrx __ret;
            __ret = SSIP.NEWS.dms_publisherPrxHelper.__read(__is);
            __result.__endReadParams();
            return __ret;
        }
        catch(Ice.LocalException ex)
        {
            Ice.Instrumentation.InvocationObserver __obsv = __result.__getObserver();
            if(__obsv != null)
            {
                __obsv.failed(ex.ice_name());
            }
            throw ex;
        }
    }

    private static final String __isMainServer_name = "isMainServer";

    public boolean isMainServer(String server)
    {
        return isMainServer(server, null, false);
    }

    public boolean isMainServer(String server, java.util.Map<String, String> __ctx)
    {
        return isMainServer(server, __ctx, true);
    }

    private boolean isMainServer(String server, java.util.Map<String, String> __ctx, boolean __explicitCtx)
    {
        if(__explicitCtx && __ctx == null)
        {
            __ctx = _emptyContext;
        }
        final Ice.Instrumentation.InvocationObserver __observer = IceInternal.ObserverHelper.get(this, "isMainServer", __ctx);
        int __cnt = 0;
        try
        {
            while(true)
            {
                Ice._ObjectDel __delBase = null;
                try
                {
                    __checkTwowayOnly("isMainServer");
                    __delBase = __getDelegate(false);
                    _dms_factoryDel __del = (_dms_factoryDel)__delBase;
                    return __del.isMainServer(server, __ctx, __observer);
                }
                catch(IceInternal.LocalExceptionWrapper __ex)
                {
                    __cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt, __observer);
                }
                catch(Ice.LocalException __ex)
                {
                    __cnt = __handleException(__delBase, __ex, null, __cnt, __observer);
                }
            }
        }
        finally
        {
            if(__observer != null)
            {
                __observer.detach();
            }
        }
    }

    public Ice.AsyncResult begin_isMainServer(String server)
    {
        return begin_isMainServer(server, null, false, null);
    }

    public Ice.AsyncResult begin_isMainServer(String server, java.util.Map<String, String> __ctx)
    {
        return begin_isMainServer(server, __ctx, true, null);
    }

    public Ice.AsyncResult begin_isMainServer(String server, Ice.Callback __cb)
    {
        return begin_isMainServer(server, null, false, __cb);
    }

    public Ice.AsyncResult begin_isMainServer(String server, java.util.Map<String, String> __ctx, Ice.Callback __cb)
    {
        return begin_isMainServer(server, __ctx, true, __cb);
    }

    public Ice.AsyncResult begin_isMainServer(String server, Callback_dms_factory_isMainServer __cb)
    {
        return begin_isMainServer(server, null, false, __cb);
    }

    public Ice.AsyncResult begin_isMainServer(String server, java.util.Map<String, String> __ctx, Callback_dms_factory_isMainServer __cb)
    {
        return begin_isMainServer(server, __ctx, true, __cb);
    }

    private Ice.AsyncResult begin_isMainServer(String server, java.util.Map<String, String> __ctx, boolean __explicitCtx, IceInternal.CallbackBase __cb)
    {
        __checkAsyncTwowayOnly(__isMainServer_name);
        IceInternal.OutgoingAsync __result = new IceInternal.OutgoingAsync(this, __isMainServer_name, __cb);
        try
        {
            __result.__prepare(__isMainServer_name, Ice.OperationMode.Idempotent, __ctx, __explicitCtx);
            IceInternal.BasicStream __os = __result.__startWriteParams(Ice.FormatType.DefaultFormat);
            __os.writeString(server);
            __result.__endWriteParams();
            __result.__send(true);
        }
        catch(Ice.LocalException __ex)
        {
            __result.__exceptionAsync(__ex);
        }
        return __result;
    }

    public boolean end_isMainServer(Ice.AsyncResult __result)
    {
        Ice.AsyncResult.__check(__result, this, __isMainServer_name);
        boolean __ok = __result.__wait();
        try
        {
            if(!__ok)
            {
                try
                {
                    __result.__throwUserException();
                }
                catch(Ice.UserException __ex)
                {
                    throw new Ice.UnknownUserException(__ex.ice_name(), __ex);
                }
            }
            IceInternal.BasicStream __is = __result.__startReadParams();
            boolean __ret;
            __ret = __is.readBool();
            __result.__endReadParams();
            return __ret;
        }
        catch(Ice.LocalException ex)
        {
            Ice.Instrumentation.InvocationObserver __obsv = __result.__getObserver();
            if(__obsv != null)
            {
                __obsv.failed(ex.ice_name());
            }
            throw ex;
        }
    }

    public static dms_factoryPrx checkedCast(Ice.ObjectPrx __obj)
    {
        dms_factoryPrx __d = null;
        if(__obj != null)
        {
            if(__obj instanceof dms_factoryPrx)
            {
                __d = (dms_factoryPrx)__obj;
            }
            else
            {
                if(__obj.ice_isA(ice_staticId()))
                {
                    dms_factoryPrxHelper __h = new dms_factoryPrxHelper();
                    __h.__copyFrom(__obj);
                    __d = __h;
                }
            }
        }
        return __d;
    }

    public static dms_factoryPrx checkedCast(Ice.ObjectPrx __obj, java.util.Map<String, String> __ctx)
    {
        dms_factoryPrx __d = null;
        if(__obj != null)
        {
            if(__obj instanceof dms_factoryPrx)
            {
                __d = (dms_factoryPrx)__obj;
            }
            else
            {
                if(__obj.ice_isA(ice_staticId(), __ctx))
                {
                    dms_factoryPrxHelper __h = new dms_factoryPrxHelper();
                    __h.__copyFrom(__obj);
                    __d = __h;
                }
            }
        }
        return __d;
    }

    public static dms_factoryPrx checkedCast(Ice.ObjectPrx __obj, String __facet)
    {
        dms_factoryPrx __d = null;
        if(__obj != null)
        {
            Ice.ObjectPrx __bb = __obj.ice_facet(__facet);
            try
            {
                if(__bb.ice_isA(ice_staticId()))
                {
                    dms_factoryPrxHelper __h = new dms_factoryPrxHelper();
                    __h.__copyFrom(__bb);
                    __d = __h;
                }
            }
            catch(Ice.FacetNotExistException ex)
            {
            }
        }
        return __d;
    }

    public static dms_factoryPrx checkedCast(Ice.ObjectPrx __obj, String __facet, java.util.Map<String, String> __ctx)
    {
        dms_factoryPrx __d = null;
        if(__obj != null)
        {
            Ice.ObjectPrx __bb = __obj.ice_facet(__facet);
            try
            {
                if(__bb.ice_isA(ice_staticId(), __ctx))
                {
                    dms_factoryPrxHelper __h = new dms_factoryPrxHelper();
                    __h.__copyFrom(__bb);
                    __d = __h;
                }
            }
            catch(Ice.FacetNotExistException ex)
            {
            }
        }
        return __d;
    }

    public static dms_factoryPrx uncheckedCast(Ice.ObjectPrx __obj)
    {
        dms_factoryPrx __d = null;
        if(__obj != null)
        {
            if(__obj instanceof dms_factoryPrx)
            {
                __d = (dms_factoryPrx)__obj;
            }
            else
            {
                dms_factoryPrxHelper __h = new dms_factoryPrxHelper();
                __h.__copyFrom(__obj);
                __d = __h;
            }
        }
        return __d;
    }

    public static dms_factoryPrx uncheckedCast(Ice.ObjectPrx __obj, String __facet)
    {
        dms_factoryPrx __d = null;
        if(__obj != null)
        {
            Ice.ObjectPrx __bb = __obj.ice_facet(__facet);
            dms_factoryPrxHelper __h = new dms_factoryPrxHelper();
            __h.__copyFrom(__bb);
            __d = __h;
        }
        return __d;
    }

    public static final String[] __ids =
    {
        "::Ice::Object",
        "::SSIP::dms_factory"
    };

    public static String ice_staticId()
    {
        return __ids[1];
    }

    protected Ice._ObjectDelM __createDelegateM()
    {
        return new _dms_factoryDelM();
    }

    protected Ice._ObjectDelD __createDelegateD()
    {
        return new _dms_factoryDelD();
    }

    public static void __write(IceInternal.BasicStream __os, dms_factoryPrx v)
    {
        __os.writeProxy(v);
    }

    public static dms_factoryPrx __read(IceInternal.BasicStream __is)
    {
        Ice.ObjectPrx proxy = __is.readProxy();
        if(proxy != null)
        {
            dms_factoryPrxHelper result = new dms_factoryPrxHelper();
            result.__copyFrom(proxy);
            return result;
        }
        return null;
    }

    public static final long serialVersionUID = 0L;
}
