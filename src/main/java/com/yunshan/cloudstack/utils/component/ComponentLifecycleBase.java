package com.yunshan.cloudstack.utils.component;

import java.util.HashMap;
import java.util.Map;

import javax.naming.ConfigurationException;

import org.apache.log4j.Logger;

public class ComponentLifecycleBase implements ComponentLifecycle {
    private static final Logger s_logger = Logger.getLogger(ComponentLifecycleBase.class);

    protected String _name;
    protected int _runLevel;
    protected Map<String, Object> _configParams = new HashMap<String, Object>();

    public ComponentLifecycleBase() {
        _name = this.getClass().getSimpleName();
        _runLevel = RUN_LEVEL_COMPONENT;
    }

    @Override
    public String getName() {
        return _name;
    }

    @Override
    public void setName(String name) {
        _name = name;
    }

    @Override
    public void setConfigParams(Map<String, Object> params) {
        _configParams = params;
    }

    @Override
    public Map<String, Object> getConfigParams() {
        return _configParams;
    }

    @Override
    public int getRunLevel() {
        return _runLevel;
    }

    @Override
    public void setRunLevel(int level) {
        _runLevel = level;
    }

    @Override
    public boolean configure(String name, Map<String, Object> params) throws ConfigurationException {
        _name = name;
        _configParams = params;
        return true;
    }

    @Override
    public boolean start() {
        return true;
    }

    @Override
    public boolean stop() {
        return true;
    }
}
