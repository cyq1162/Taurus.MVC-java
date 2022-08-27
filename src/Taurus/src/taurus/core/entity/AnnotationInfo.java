package taurus.core.entity;

import taurus.core.annotation.Require;

public class AnnotationInfo {

	private boolean _HasRequire;
    public Boolean getHasRequire()
    {
    	return _HasRequire;
    }
    public void setHasRequire(Boolean value) {
		this._HasRequire=value;
	}
    
	private Require[] _RequireAttrs;
    public Require[] getRequireAttrs()
    {
    	return _RequireAttrs;
    }
    public void setRequireAttrs(Require[] value) {
		this._RequireAttrs=value;
		
	}
    private RequireInfo[] _RequireInfos;
    public RequireInfo[] getRequireInfos() {
    	if(this._RequireAttrs==null){return null;}
    	if(_RequireInfos==null)
    	{
    		_RequireInfos=new RequireInfo[this._RequireAttrs.length];
			for (int i = 0; i < _RequireInfos.length; i++) {
				_RequireInfos[i]=new RequireInfo(this._RequireAttrs[i]);
			}
    	}
		return _RequireInfos;
	}
    
    
	private boolean _HasToken;
    public Boolean getHasToken()
    {
    	return _HasToken;
    }
    public void setHasToken(Boolean value) {
		this._HasToken=value;
	}
    
	private boolean _HasAck;
    public Boolean getHasAck()
    {
    	return _HasAck;
    }
    public void setHasAck(Boolean value) {
		this._HasAck=value;
	}
    
    private boolean _HasMicroService ;
    public Boolean getHasMicroService ()
    {
    	return _HasMicroService ;
    }
    public void setHasMicroService (Boolean value) {
		this._HasMicroService =value;
	}

    private boolean _HasHttpGet  ;
    public Boolean getHasHttpGet  ()
    {
    	return _HasHttpGet ;
    }
    public void setHasHttpGet(Boolean value) {
		this._HasHttpGet =value;
	}
    
    private boolean _HasHttpPost  ;
    public Boolean getHasHttpPost  ()
    {
    	return _HasHttpPost ;
    }
    public void setHasHttpPost(Boolean value) {
		this._HasHttpPost =value;
	}
    
    private boolean _HasHttpHead  ;
    public Boolean getHasHttpHead ()
    {
    	return _HasHttpHead ;
    }
    public void setHasHttpHead(Boolean value) {
		this._HasHttpHead =value;
	}

    private boolean _HasHttpPut ;
    public Boolean getHasHttpPut ()
    {
    	return _HasHttpPut ;
    }
    public void setHasHttpPut(Boolean value) {
		this._HasHttpPut =value;
	}
    
    private boolean _HasHttpDelete;
    public Boolean getHasHttpDelete()
    {
    	return _HasHttpDelete ;
    }
    public void setHasHttpDelete(Boolean value) {
		this._HasHttpDelete =value;
	}

    

    /// <summary>
    /// 是否包含指定的key
    /// </summary>
    /// <param name="key">token、ack、get、post、head、put、delete</param>
    /// <returns></returns>
    public Boolean HasKey(String key)
    {
        // internal static string[] HttpMethods = new string[] { "GET", "POST", "HEAD", "PUT", "DELETE" };
        if (key==null || key.length()==0) { return false; }
        key=key.toLowerCase();
        if(key.equals("token"))
        {
        	return _HasToken;
        }
        if(key.equals("ack"))
        {
        	return _HasAck;
        }
        if(key.equals("microservice"))
        {
        	return _HasMicroService;
        }
        if(key.equals("require"))
        {
        	return _HasRequire;
        }
        if (!_HasHttpGet && !_HasHttpPost && !_HasHttpHead && !_HasHttpPut && !_HasHttpDelete)//无配置，则都可以。
        {
            return true;
        }
        else
        {
        	if(key.equals("get"))
            {
            	return _HasHttpGet;
            }
            if(key.equals("post"))
            {
            	return _HasHttpPost;
            }
            if(key.equals("head"))
            {
            	return _HasHttpHead;
            }
            if(key.equals("put"))
            {
            	return _HasHttpPut;
            }
            if(key.equals("delete"))
            {
            	return _HasHttpDelete;
            }
                return false;
        }
        
    }
}
