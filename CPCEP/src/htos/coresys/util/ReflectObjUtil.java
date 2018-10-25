package htos.coresys.util;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @function：设置单个属性
 * @date : 
 */
@SuppressWarnings("rawtypes")
public class ReflectObjUtil {
    /** 正则表达式 用于匹配属性的第一个字母 {@value [a-zA-Z]} **/
    private static final String REGEX = "[a-zA-Z]";
    public static void setAttrributeValue(Object obj,String attribute,Object value)
    {
        String method_name = convertToMethodName(attribute,obj.getClass(),true);
        Method[] methods = obj.getClass().getMethods();
        for (Method method : methods) {
            /**
             *     因为这里只是调用bean中属性的set方法，属性名称不能重复
             * 所以set方法也不会重复，所以就直接用方法名称去锁定一个方法
             * （注：在java中，锁定一个方法的条件是方法名及参数）
             * **/
            if(method.getName().equals(method_name))
            {
                Class[] parameterC = method.getParameterTypes();
                try {
                    /**如果是基本数据类型时（如int、float、double、byte、char、boolean）
                     * 需要先将Object转换成相应的封装类之后再转换成对应的基本数据类型
                     * 否则会报 ClassCastException**/
                    if(parameterC[0] == int.class)
                    {
                        method.invoke(obj,((Integer)value).intValue());
                        break;
                    }else if(parameterC[0] == float.class){
                        method.invoke(obj, ((Float)value).floatValue());
                        break;
                    }else if(parameterC[0] == double.class)
                    {
                        method.invoke(obj, ((Double)value).doubleValue());
                        break;
                    }else if(parameterC[0] == byte.class)
                    {
                        method.invoke(obj, ((Byte)value).byteValue());
                        break;
                    }else if(parameterC[0] == char.class)
                    {
                        method.invoke(obj, ((Character)value).charValue());
                        break;
                    }else if(parameterC[0] == boolean.class)
                    {
                        method.invoke(obj, ((Boolean)value).booleanValue());
                        break;
                    }else
                    {
                        method.invoke(obj,parameterC[0].cast(value));
                        break;
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                } catch (IllegalAccessException e) {
                    System.out.println(e.getMessage());
                } catch (InvocationTargetException e) {
                    System.out.println(e.getMessage());
                } catch (SecurityException e) {
                    System.out.println(e.getMessage());
                } 
            }
        }
    }
    /**
     * @function：以Map形式设置多个属性
     * @date : 
     */
    public static void setAttrributesValues(Object obj,Map<String,Object> map)
    {
    	Map<String,Object> method_name = new HashMap<String, Object>();
    	for(String str : map.keySet()){
    		method_name.put(convertToMethodName(str,obj.getClass(),true), map.get(str));
    	}
        Method[] methods = obj.getClass().getMethods();
        for (Method method : methods) {
            /**
             * 因为这里只是调用bean中属性的set方法，属性名称不能重复
             * 所以set方法也不会重复，所以就直接用方法名称去锁定一个方法
             * （注：在java中，锁定一个方法的条件是方法名及参数）
             * **/
            if(method_name.containsKey(method.getName()))
            {
                Class[] parameterC = method.getParameterTypes();
                try {
                    /**如果是基本数据类型时（如int、float、double、byte、char、boolean）
                     * 需要先将Object转换成相应的封装类之后再转换成对应的基本数据类型
                     * 否则会报 ClassCastException**/
                    if(parameterC[0] == int.class)
                    {
                        method.invoke(obj,((Integer)method_name.get(method.getName())).intValue());
                    }else if(parameterC[0] == float.class){
                        method.invoke(obj, ((Float)method_name.get(method.getName())).floatValue());
                    }else if(parameterC[0] == double.class)
                    {
                        method.invoke(obj, ((Double)method_name.get(method.getName())).doubleValue());
                    }else if(parameterC[0] == byte.class)
                    {
                        method.invoke(obj, ((Byte)method_name.get(method.getName())).byteValue());
                    }else if(parameterC[0] == char.class)
                    {
                        method.invoke(obj, ((Character)method_name.get(method.getName())).charValue());
                    }else if(parameterC[0] == boolean.class)
                    {
                        method.invoke(obj, ((Boolean)method_name.get(method.getName())).booleanValue());
                    }else
                    {
                        method.invoke(obj,parameterC[0].cast(method_name.get(method.getName())));
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                } catch (IllegalAccessException e) {
                    System.out.println(e.getMessage());
                } catch (InvocationTargetException e) {
                    System.out.println(e.getMessage());
                } catch (SecurityException e) {
                    System.out.println(e.getMessage());
                } 
            }
        }
    }
    
    private static String convertToMethodName(String attribute,Class objClass,boolean isSet)
    {
        /** 通过正则表达式来匹配第一个字符 **/
        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(attribute);
        StringBuilder sb = new StringBuilder();
        /** 如果是set方法名称 **/
        if(isSet)
        {
            sb.append("set");
        }else{
        /** get方法名称 **/
            try {
                Field attributeField = objClass.getDeclaredField(attribute);
                /** 如果类型为boolean **/
                if(attributeField.getType() == boolean.class||attributeField.getType() == Boolean.class)
                {
                    sb.append("is");
                }else
                {
                    sb.append("get");
                }
            } catch (SecurityException e) {
                System.out.println(e.getMessage());
            } catch (NoSuchFieldException e) {
                System.out.println(e.getMessage());
            }catch (Exception e) {
            	 System.out.println(e.getMessage());
			}
        }
        /** 针对以下划线开头的属性 **/
        if(attribute.charAt(0)!='_' && m.find())
        {
            sb.append(m.replaceFirst(m.group().toUpperCase()));
        }else{
            sb.append(attribute);
        }
        return sb.toString();
    }
    
    /**
     * @describe : 获取obj对象的单个属性
     * @param obj
     * @param attribute
     * @return object
     */
    public static Object getAttrributeValue(Object obj,String attribute)
    {
        Object value = null;
        try {
        	 String methodName = convertToMethodName(attribute, obj.getClass(), false);
            /** 由于get方法没有参数且唯一，所以直接通过方法名称锁定方法 **/
            Method methods = obj.getClass().getDeclaredMethod(methodName);
            if(methods != null)
            {
                value = methods.invoke(obj);
            }
        } catch (SecurityException e) {
            System.out.println(e.getMessage());
        } catch (NoSuchMethodException e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());
        } catch (InvocationTargetException e) {
            System.out.println(e.getMessage());
        }
        return value;
    }
    
    /**
     * @describe : 获取obj对象的单个属性
     * @param obj
     * @param attribute
     * @return map
     */
    public static Map<String,Object> getAttrributesValues(Object obj,Set<String> attrNameSet)
    {
    	Map<String,Object> map =new HashMap<String,Object>();
    	String methodName = null;
    	Method methods =null;
    	for(String s:attrNameSet){
            try {
                /** 由于get方法没有参数且唯一，所以直接通过方法名称锁定方法 **/
            	methodName = convertToMethodName(s, obj.getClass(), false);
            	methods = obj.getClass().getDeclaredMethod(methodName);
        		if(methods != null)
                {
        			//methods.invoke(obj);
        			map.put(s, methods.invoke(obj));
                }
            } catch (SecurityException e) {
                System.out.println(e.getMessage());
            } catch (NoSuchMethodException e) {
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (IllegalAccessException e) {
                System.out.println(e.getMessage());
            } catch (InvocationTargetException e) {
                System.out.println(e.getMessage());
            }
    	
    	}
        return map;
    }
    
    /**
     * @describe : 获取子类继承父类对象的单个属性(一级继承)
     * @param obj
     * @param attribute
     * @return object
     */
    public static Object getAttrributeParentOneValue(Object obj,String attribute)
    {
       
        Object value = null;
        try {
        	 String methodName = convertToMethodName(attribute, obj.getClass().getSuperclass(), false);
        		/** 由于get方法没有参数且唯一，所以直接通过方法名称锁定方法 **/
                Method methods = obj.getClass().getSuperclass().getDeclaredMethod(methodName);
                if(methods != null)
                {
                    value = methods.invoke(obj);
                }
        } catch (SecurityException e) {
        	value = getAttrributeValue(obj, attribute);
            System.out.println(e.getMessage());
        } catch (NoSuchMethodException e) {
        	value = getAttrributeValue(obj, attribute);
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
        	value = getAttrributeValue(obj, attribute);
            System.out.println(e.getMessage());
        } catch (IllegalAccessException e) {
        	value = getAttrributeValue(obj, attribute);
            System.out.println(e.getMessage());
        } catch (InvocationTargetException e) {
        	value = getAttrributeValue(obj, attribute);
            System.out.println(e.getMessage());
        }catch (Exception e) {
        	value = getAttrributeValue(obj, attribute);
       	 	System.out.println(e.getMessage());
		}
        return value;
    }
    /**
     * @describe : 获取子类继承父类对象的单个属性(二级继承)
     * @param obj
     * @param attribute
     * @return object
     */
    public static Object getAttrributeParentTwoValue(Object obj,String attribute)
    {
        Object value = null;
        try {
        	 String methodName  = convertToMethodName(attribute, obj.getClass().getSuperclass().getSuperclass(), false);
    		/** 由于get方法没有参数且唯一，所以直接通过方法名称锁定方法 **/
            Method methods = obj.getClass().getSuperclass().getSuperclass().getDeclaredMethod(methodName);
            if(methods != null)
            {
                value = methods.invoke(obj);
            }
        } catch (SecurityException e) {
        	value = getAttrributeParentOneValue(obj, attribute);
            System.out.println(e.getMessage());
        } catch (NoSuchMethodException e) {
        	value = getAttrributeParentOneValue(obj, attribute);
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
        	value = getAttrributeParentOneValue(obj, attribute);
            System.out.println(e.getMessage());
        } catch (IllegalAccessException e) {
        	value = getAttrributeParentOneValue(obj, attribute);
            System.out.println(e.getMessage());
        } catch (InvocationTargetException e) {
        	value = getAttrributeParentOneValue(obj, attribute);
            System.out.println(e.getMessage());
        }catch (Exception e) {
        	value = getAttrributeParentOneValue(obj, attribute);
            System.out.println(e.getMessage());
		}
        return value;
    }
    /**
     * @describe : 获取子类继承父类对象的单个属性(三级继承)
     * @param obj
     * @param attribute
     * @return object
     */
    public static Object getAttrributeParentThreeValue(Object obj,String attribute)
    {
        Object value = null;
        try {
        	String methodName  = convertToMethodName(attribute, obj.getClass().getSuperclass().getSuperclass().getSuperclass(), false);
    		/** 由于get方法没有参数且唯一，所以直接通过方法名称锁定方法 **/
            Method methods = obj.getClass().getSuperclass().getSuperclass().getSuperclass().getDeclaredMethod(methodName);
            if(methods != null)
            {
                value = methods.invoke(obj);
            }
        } catch (SecurityException e) {
        	value = getAttrributeParentTwoValue(obj, attribute);
            System.out.println(e.getMessage());
        } catch (NoSuchMethodException e) {
        	value = getAttrributeParentTwoValue(obj, attribute);
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
        	value = getAttrributeParentTwoValue(obj, attribute);
            System.out.println(e.getMessage());
        } catch (IllegalAccessException e) {
        	value = getAttrributeParentTwoValue(obj, attribute);
            System.out.println(e.getMessage());
        } catch (InvocationTargetException e) {
        	value = getAttrributeParentTwoValue(obj, attribute);
            System.out.println(e.getMessage());
        }catch (Exception e) {
        	value = getAttrributeParentTwoValue(obj, attribute);
            System.out.println(e.getMessage());
		}
        return value;
    }
    
    /**
     * @describe : 获取map对象的单个属性(一级继承)
     * @param obj
     * @param attribute
     * @return map
     */
    public static Map<String,Object> getAttrributesParentOneValues(Object obj,Set<String> attrNameSet)
    {
    	Map<String,Object> map =new HashMap<String,Object>();
    	for(String s:attrNameSet){
            try {
                /** 由于get方法没有参数且唯一，所以直接通过方法名称锁定方法 **/
            	String	methodName = convertToMethodName(s, obj.getClass().getSuperclass(), false);
            	Method methods = obj.getClass().getSuperclass().getDeclaredMethod(methodName);
            	if(methods != null){
            		map.put(s, methods.invoke(obj));
                }
            } catch (SecurityException e) {
            	map.put(s, getAttrributeValue(obj,s));
                System.out.println(e.getMessage());
            } catch (NoSuchMethodException e) {
            	map.put(s, getAttrributeValue(obj,s));
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {
            	map.put(s, getAttrributeValue(obj,s));
                System.out.println(e.getMessage());
            } catch (IllegalAccessException e) {
            	map.put(s, getAttrributeValue(obj,s));
                System.out.println(e.getMessage());
            } catch (InvocationTargetException e) {
            	map.put(s, getAttrributeValue(obj,s));
                System.out.println(e.getMessage());
            }catch (Exception e) {
            	map.put(s, getAttrributeValue(obj,s));
                System.out.println(e.getMessage());
    		}
    	
    	}
        return map;
    }
    
    /**
     * @describe : 获取map对象的单个属性(二级继承)
     * @param obj
     * @param attribute
     * @return map
     */
    public static Map<String,Object> getAttrributesParentTwoValues(Object obj,Set<String> attrNameSet)
    {
    	Map<String,Object> map =new HashMap<String,Object>();
    	for(String s:attrNameSet){
            try {
                /** 由于get方法没有参数且唯一，所以直接通过方法名称锁定方法 **/
            	String	methodName = convertToMethodName(s, obj.getClass().getSuperclass().getSuperclass(), false);
            	Method methods = obj.getClass().getSuperclass().getSuperclass().getDeclaredMethod(methodName);
            	if(methods != null){
            		map.put(s, methods.invoke(obj));
                }
            } catch (SecurityException e) {
            	map.put(s, getAttrributeParentOneValue(obj,s));
                System.out.println(e.getMessage());
            } catch (NoSuchMethodException e) {
            	map.put(s, getAttrributeParentOneValue(obj,s));
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {
            	map.put(s, getAttrributeParentOneValue(obj,s));
                System.out.println(e.getMessage());
            } catch (IllegalAccessException e) {
            	map.put(s, getAttrributeParentOneValue(obj,s));
                System.out.println(e.getMessage());
            } catch (InvocationTargetException e) {
            	map.put(s, getAttrributeParentOneValue(obj,s));
                System.out.println(e.getMessage());
            }catch (Exception e) {
            	map.put(s, getAttrributeParentOneValue(obj,s));
                System.out.println(e.getMessage());
    		}
    	
    	}
        return map;
    }
    
    /**
     * @describe : 获取map对象的单个属性(三级继承)
     * @param obj
     * @param attribute
     * @return map
     */
    public static Map<String,Object> getAttrributesParentThreeValues(Object obj,Set<String> attrNameSet)
    {
    	Map<String,Object> map =new HashMap<String,Object>();
    	for(String s:attrNameSet){
            try {
                /** 由于get方法没有参数且唯一，所以直接通过方法名称锁定方法 **/
            	String	methodName = convertToMethodName(s, obj.getClass().getSuperclass().getSuperclass().getSuperclass(), false);
            	Method methods = obj.getClass().getSuperclass().getSuperclass().getSuperclass().getDeclaredMethod(methodName);
            	if(methods != null){
            		map.put(s, methods.invoke(obj));
                }
            } catch (SecurityException e) {
            	map.put(s, getAttrributeParentTwoValue(obj,s));
                System.out.println(e.getMessage());
            } catch (NoSuchMethodException e) {
            	map.put(s, getAttrributeParentTwoValue(obj,s));
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {
            	map.put(s, getAttrributeParentTwoValue(obj,s));
                System.out.println(e.getMessage());
            } catch (IllegalAccessException e) {
            	map.put(s, getAttrributeParentTwoValue(obj,s));
                System.out.println(e.getMessage());
            } catch (InvocationTargetException e) {
            	map.put(s, getAttrributeParentTwoValue(obj,s));
                System.out.println(e.getMessage());
            }catch (Exception e) {
            	map.put(s, getAttrributeParentTwoValue(obj,s));
                System.out.println(e.getMessage());
    		}
    	
    	}
        return map;
    }
    
    public static void main(String[] args) {
    	/*
    	ScheduleModel model = new ScheduleModel();
    	model.setUuid("12344");
    	model.setProjectNo("sss");
    	Set<String> set = new HashSet<String>();
    	set.add("projectNo");
    	set.add("uuid");
    	
    	System.out.println(ReflectObjUtil.getAttrributeParentThreeValue(model, "uuid")+"对象输出");//根据继承级别调用相应方法
    	//返回map方法
    	Map<String, Object> map = ReflectObjUtil.getAttrributesParentThreeValues(model, set);
    	for (String str : map.keySet()) {
			System.out.println("key:"+str+"\n value:"+map.get(str)+"map输出");
		}
    	 */
	}
}