package com.fsti.fjdicClient.util.asyncUtil;

/** 
 * ���۲��� 
 * @ClassName: ProgressCallable 
 * @param <T> 
 */  
public interface ProgressCallable<T> {  
  
    /** 
     * ע��۲��߶��� 
     * @param pProgressListener 
     * @return 
     * @throws Exception 
     */  
    public T call(final IProgressListener pProgressListener) throws Exception;  
}  