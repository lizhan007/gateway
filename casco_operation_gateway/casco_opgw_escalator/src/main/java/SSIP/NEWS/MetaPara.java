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

package SSIP.NEWS;

public class MetaPara implements Cloneable, java.io.Serializable
{
    public int index;

    public String pointcode;

    public String table;

    public String field;

    public byte type;

    public MetaPara()
    {
    }

    public MetaPara(int index, String pointcode, String table, String field, byte type)
    {
        this.index = index;
        this.pointcode = pointcode;
        this.table = table;
        this.field = field;
        this.type = type;
    }

    public boolean
    equals(Object rhs)
    {
        if(this == rhs)
        {
            return true;
        }
        MetaPara _r = null;
        if(rhs instanceof MetaPara)
        {
            _r = (MetaPara)rhs;
        }

        if(_r != null)
        {
            if(index != _r.index)
            {
                return false;
            }
            if(pointcode != _r.pointcode)
            {
                if(pointcode == null || _r.pointcode == null || !pointcode.equals(_r.pointcode))
                {
                    return false;
                }
            }
            if(table != _r.table)
            {
                if(table == null || _r.table == null || !table.equals(_r.table))
                {
                    return false;
                }
            }
            if(field != _r.field)
            {
                if(field == null || _r.field == null || !field.equals(_r.field))
                {
                    return false;
                }
            }
            if(type != _r.type)
            {
                return false;
            }

            return true;
        }

        return false;
    }

    public int
    hashCode()
    {
        int __h = 5381;
        __h = IceInternal.HashUtil.hashAdd(__h, "::SSIP::NEWS::MetaPara");
        __h = IceInternal.HashUtil.hashAdd(__h, index);
        __h = IceInternal.HashUtil.hashAdd(__h, pointcode);
        __h = IceInternal.HashUtil.hashAdd(__h, table);
        __h = IceInternal.HashUtil.hashAdd(__h, field);
        __h = IceInternal.HashUtil.hashAdd(__h, type);
        return __h;
    }

    public Object
    clone()
    {
        Object o = null;
        try
        {
            o = super.clone();
        }
        catch(CloneNotSupportedException ex)
        {
            assert false; // impossible
        }
        return o;
    }

    public void
    __write(IceInternal.BasicStream __os)
    {
        __os.writeInt(index);
        __os.writeString(pointcode);
        __os.writeString(table);
        __os.writeString(field);
        __os.writeByte(type);
    }

    public void
    __read(IceInternal.BasicStream __is)
    {
        index = __is.readInt();
        pointcode = __is.readString();
        table = __is.readString();
        field = __is.readString();
        type = __is.readByte();
    }

    public static final long serialVersionUID = 554223326L;
}