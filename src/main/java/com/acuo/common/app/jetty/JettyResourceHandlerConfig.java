package com.acuo.common.app.jetty;

import org.eclipse.jetty.http.MimeTypes;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.resource.Resource;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Config for serving static files.
 *
 * @see ResourceHandler
 */
public final class JettyResourceHandlerConfig {
    private Resource baseResource;
    private String cacheControlHeader;
    private boolean directoryListing = false;
    private boolean etags = false;
    private MimeTypes mimeTypes;

    private String stylesheetPath;
    private List<String> welcomeFiles;

    @Nonnull
    private String contextPath = "/";

    public void setBaseResource(Resource baseResource) {
        this.baseResource = baseResource;
    }

    public void setCacheControlHeader(String cacheControlHeader) {
        this.cacheControlHeader = cacheControlHeader;
    }

    public void setDirectoryListing(boolean directoryListing) {
        this.directoryListing = directoryListing;
    }

    public void setEtags(boolean etags) {
        this.etags = etags;
    }

    public void setMimeTypes(MimeTypes mimeTypes) {
        this.mimeTypes = mimeTypes;
    }

    public void setStylesheetPath(String stylesheetPath) {
        this.stylesheetPath = stylesheetPath;
    }

    public void setWelcomeFiles(List<String> welcomeFiles) {
        this.welcomeFiles = welcomeFiles;
    }

    public void setContextPath(@Nonnull String contextPath) {
        this.contextPath = contextPath;
    }

    public JettyResourceHandlerConfig withBaseResource(Resource baseResource) {
        setBaseResource(baseResource);
        return this;
    }

    public JettyResourceHandlerConfig withCacheControlHeader(String cacheControlHeader) {
        setCacheControlHeader(cacheControlHeader);
        return this;
    }

    public JettyResourceHandlerConfig withDirectoryListing(boolean directoryListing) {
        setDirectoryListing(directoryListing);
        return this;
    }

    public JettyResourceHandlerConfig withEtags(boolean etags) {
        setEtags(etags);
        return this;
    }

    public JettyResourceHandlerConfig withMimeTypes(MimeTypes mimeTypes) {
        setMimeTypes(mimeTypes);
        return this;
    }

    public JettyResourceHandlerConfig withStylesheetPath(String stylesheetPath) {
        setStylesheetPath(stylesheetPath);
        return this;
    }

    public JettyResourceHandlerConfig withWelcomeFiles(List<String> welcomeFiles) {
        setWelcomeFiles(welcomeFiles);
        return this;
    }

    public JettyResourceHandlerConfig withContextPath(@Nonnull String contextPath) {
        setContextPath(contextPath);
        return this;
    }

    @Nonnull
    ContextHandler buildHandler() {
        ResourceHandler rh = new ResourceHandler();

        rh.setBaseResource(baseResource);

        if (cacheControlHeader != null) {
            rh.setCacheControl(cacheControlHeader);
        }

        rh.setDirectoriesListed(directoryListing);
        rh.setEtags(etags);

        if (mimeTypes != null) {
            rh.setMimeTypes(mimeTypes);
        }

        if (stylesheetPath != null) {
            rh.setStylesheet(stylesheetPath);
        }

        if (welcomeFiles != null) {
            rh.setWelcomeFiles(welcomeFiles.toArray(new String[welcomeFiles.size()]));
        }

        ContextHandler ch = new ContextHandler();
        ch.setContextPath(contextPath);
        ch.setHandler(rh);

        return ch;
    }
}
