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

public abstract class Callback_dms_factory_getDaq extends Ice.TwowayCallback
{
    public abstract void response(DAQPrx __ret);

    public final void __completed(Ice.AsyncResult __result)
    {
        dms_factoryPrx __proxy = (dms_factoryPrx)__result.getProxy();
        DAQPrx __ret = null;
        try
        {
            __ret = __proxy.end_getDaq(__result);
        }
        catch(Ice.LocalException __ex)
        {
            exception(__ex);
            return;
        }
        response(__ret);
    }
}
