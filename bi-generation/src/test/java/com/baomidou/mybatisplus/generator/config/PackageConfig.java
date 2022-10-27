package com.baomidou.mybatisplus.generator.config;




import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PackageConfig {
    private String parent;
    private String moduleName;
    private String entity;
    private String service;
    private String serviceImpl;
    private String mapper;
    private String xml;
    private String controller;
    private String other;
    private String packageEntity;
    private String packageController;
    private String packageMapper;
    private String packageService;
    private String packageServiceImpl;
    private Map<OutputFile, String> pathInfo;
    private final Map<String, String> packageInfo;

    private PackageConfig() {
        this.parent = "com.baomidou";
        this.moduleName = "";
        this.entity = "entity";
        this.service = "service";
        this.serviceImpl = "service.impl";
        this.mapper = "mapper";
        this.xml = "mapper.xml";
        this.controller = "controller";
        this.other = "other";
        this.packageInfo = new HashMap();
    }


    public String getParent() {
        return StringUtils.isNotBlank(this.moduleName) ? this.parent + "." + this.moduleName : this.parent;
    }


    public String joinPackage(String subPackage) {
        String parent = this.getParent();
        return StringUtils.isBlank(parent) ? subPackage : parent + "." + subPackage;
    }


    public Map<String, String> getPackageInfo() {
        if (this.packageInfo.isEmpty()) {
            this.packageInfo.put("ModuleName", this.getModuleName());
            this.packageInfo.put("Entity", this.joinPackage(this.getEntity()));
            this.packageInfo.put("Mapper", this.joinPackage(this.getMapper()));
            this.packageInfo.put("Xml", this.joinPackage(this.getXml()));
            this.packageInfo.put("Service", this.joinPackage(this.getService()));
            this.packageInfo.put("ServiceImpl", this.joinPackage(this.getServiceImpl()));
            this.packageInfo.put("Controller", this.joinPackage(this.getController()));
            this.packageInfo.put("Other", this.joinPackage(this.getOther()));
            this.packageInfo.put("Parent", this.getParent());
            this.packageInfo.put("PackageEntity", this.getPackageEntity());
            this.packageInfo.put("PackageServiceImpl", this.getPackageServiceImpl());
            this.packageInfo.put("PackageService", this.getPackageService());
            this.packageInfo.put("PackageMapper", this.getPackageMapper());
            this.packageInfo.put("PackageController", this.getPackageController());
        }

        return Collections.unmodifiableMap(this.packageInfo);
    }

    public String getPackageInfo(String module) {
        return (String)this.getPackageInfo().get(module);
    }

    public String getModuleName() {
        return this.moduleName;
    }
    public String getPackageEntity() {
        return this.packageEntity;
    }
    public String getPackageServiceImpl() {
        return this.packageServiceImpl;
    }
    public String getPackageService() {
        return this.packageService;
    }
    public String getPackageMapper() {
        return this.packageMapper;
    }
    public String getPackageController() {
        return this.packageController;
    }

    public String getEntity() {
        return this.entity;
    }

    public String getService() {
        return this.service;
    }

    public String getServiceImpl() {
        return this.serviceImpl;
    }

    public String getMapper() {
        return this.mapper;
    }

    public String getXml() {
        return this.xml;
    }

    public String getController() {
        return this.controller;
    }

    public String getOther() {
        return this.other;
    }

    public Map<OutputFile, String> getPathInfo() {
        return this.pathInfo;
    }

    public static class Builder implements IConfigBuilder<PackageConfig> {
        private final PackageConfig packageConfig;

        public Builder() {
            this.packageConfig = new PackageConfig();
        }

        public Builder(@org.jetbrains.annotations.NotNull String parent,  String moduleName) {
            this();
            this.packageConfig.parent = parent;
            this.packageConfig.moduleName = moduleName;
        }

        public PackageConfig.Builder parent( String parent) {
            this.packageConfig.parent = parent;
            return this;
        }

        public PackageConfig.Builder moduleName( String moduleName) {
            this.packageConfig.moduleName = moduleName;
            return this;
        }

        public PackageConfig.Builder entity( String entity) {
            this.packageConfig.entity = entity;
            return this;
        }
        public PackageConfig.Builder packageEntity( String packageEntity) {
            this.packageConfig.packageEntity = packageEntity;
            return this;
        }
        public PackageConfig.Builder packageController( String packageController) {
            this.packageConfig.packageController = packageController;
            return this;
        }
        public PackageConfig.Builder packageMapper( String packageMapper) {
            this.packageConfig.packageMapper = packageMapper;
            return this;
        }
        public PackageConfig.Builder packageService( String packageService) {
            this.packageConfig.packageService = packageService;
            return this;
        }
        public PackageConfig.Builder packageServiceImpl( String packageServiceImpl) {
            this.packageConfig.packageServiceImpl = packageServiceImpl;
            return this;
        }
        public PackageConfig.Builder service( String service) {
            this.packageConfig.service = service;
            return this;
        }

        public PackageConfig.Builder serviceImpl( String serviceImpl) {
            this.packageConfig.serviceImpl = serviceImpl;
            return this;
        }

        public PackageConfig.Builder mapper( String mapper) {
            this.packageConfig.mapper = mapper;
            return this;
        }

        public PackageConfig.Builder xml( String xml) {
            this.packageConfig.xml = xml;
            return this;
        }

        public PackageConfig.Builder controller( String controller) {
            this.packageConfig.controller = controller;
            return this;
        }

        public PackageConfig.Builder other( String other) {
            this.packageConfig.other = other;
            return this;
        }

        public PackageConfig.Builder pathInfo( Map<OutputFile, String> pathInfo) {
            this.packageConfig.pathInfo = pathInfo;
            return this;
        }


        public String joinPackage( String subPackage) {
            return this.packageConfig.joinPackage(subPackage);
        }

        public PackageConfig build() {
            return this.packageConfig;
        }
    }
}
