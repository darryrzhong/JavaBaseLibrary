package com.example.corelibrary.net.retrofit;


import androidx.annotation.Nullable;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;

/**
 * 应用模块:
 * 
 * 类名称:ParameterizedTypeImpl.java
 * 
 * 类描述: 解析响应类型
 *
 * @author darryrzhong
 * @since 2019/8/29 23:55
 */
public class ParameterizedTypeImpl implements ParameterizedType
{
    
    private final Type ownerType;
    
    private final Type rawType;
    
    private final Type[] typeArguments;
    
    public ParameterizedTypeImpl(@Nullable Type ownerType, Type rawType,
                                 Type... typeArguments)
    {
        // Require an owner type if the raw type needs it.
        if (rawType instanceof Class<?>
            && (ownerType == null) != (((Class<?>)rawType)
                .getEnclosingClass() == null))
        {
            throw new IllegalArgumentException();
        }
        
        for (Type typeArgument : typeArguments)
        {
            checkNotNull(typeArgument, "typeArgument == null");
            checkNotPrimitive(typeArgument);
        }
        
        this.ownerType = ownerType;
        this.rawType = rawType;
        this.typeArguments = typeArguments.clone();
    }
    
    static void checkNotPrimitive(Type type)
    {
        if (type instanceof Class<?> && ((Class<?>)type).isPrimitive())
        {
            throw new IllegalArgumentException();
        }
    }
    
    static <T> T checkNotNull(@Nullable T object, String message)
    {
        if (object == null)
        {
            throw new NullPointerException(message);
        }
        return object;
    }
    
    static String typeToString(Type type)
    {
        return type instanceof Class ? ((Class<?>)type).getName()
            : type.toString();
    }
    
    /**
     * Returns true if {@code a} and {@code b} are equal.
     */
    static boolean typeEquals(Type a, Type b)
    {
        if (a == b)
        {
            return true; // Also handles (a == null && b == null).
            
        }
        else if (a instanceof Class)
        {
            return a.equals(b); // Class already specifies equals().
            
        }
        else if (a instanceof ParameterizedType)
        {
            if (!(b instanceof ParameterizedType))
            {
                return false;
            }
            ParameterizedType pa = (ParameterizedType)a;
            ParameterizedType pb = (ParameterizedType)b;
            Object ownerA = pa.getOwnerType();
            Object ownerB = pb.getOwnerType();
            return (ownerA == ownerB
                || (ownerA != null && ownerA.equals(ownerB)))
                && pa.getRawType().equals(pb.getRawType())
                && Arrays.equals(pa.getActualTypeArguments(),
                    pb.getActualTypeArguments());
            
        }
        else if (a instanceof GenericArrayType)
        {
            if (!(b instanceof GenericArrayType))
            {
                return false;
            }
            GenericArrayType ga = (GenericArrayType)a;
            GenericArrayType gb = (GenericArrayType)b;
            return typeEquals(ga.getGenericComponentType(),
                gb.getGenericComponentType());
            
        }
        else if (a instanceof WildcardType)
        {
            if (!(b instanceof WildcardType))
            {
                return false;
            }
            WildcardType wa = (WildcardType)a;
            WildcardType wb = (WildcardType)b;
            return Arrays.equals(wa.getUpperBounds(), wb.getUpperBounds())
                && Arrays.equals(wa.getLowerBounds(), wb.getLowerBounds());
            
        }
        else if (a instanceof TypeVariable)
        {
            if (!(b instanceof TypeVariable))
            {
                return false;
            }
            TypeVariable<?> va = (TypeVariable<?>)a;
            TypeVariable<?> vb = (TypeVariable<?>)b;
            return va.getGenericDeclaration() == vb.getGenericDeclaration()
                && va.getName().equals(vb.getName());
            
        }
        else
        {
            // This isn't a type we support!
            return false;
        }
    }
    
    @Override
    public Type[] getActualTypeArguments()
    {
        return typeArguments.clone();
    }
    
    @Override
    public Type getRawType()
    {
        return rawType;
    }
    
    @Override
    public Type getOwnerType()
    {
        return ownerType;
    }
    
    @Override
    public boolean equals(Object other)
    {
        return other instanceof ParameterizedType
            && typeEquals(this, (ParameterizedType)other);
    }
    
    @Override
    public int hashCode()
    {
        return Arrays.hashCode(typeArguments) ^ rawType.hashCode()
            ^ (ownerType != null ? ownerType.hashCode() : 0);
    }
    
    @Override
    public String toString()
    {
        if (typeArguments.length == 0)
        {
            return typeToString(rawType);
        }
        StringBuilder result =
            new StringBuilder(30 * (typeArguments.length + 1));
        result.append(typeToString(rawType));
        result.append("<").append(typeToString(typeArguments[0]));
        for (int i = 1; i < typeArguments.length; i++)
        {
            result.append(", ").append(typeToString(typeArguments[i]));
        }
        return result.append(">").toString();
    }
    
}
