package com.acuo.common.app.jetty;

import org.eclipse.jetty.util.ssl.SslContextFactory;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;
import java.security.KeyStore;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;

/**
 * Config for an individual connector that will be used in a {@link JettyServerWrapper}. Defaults, where provided, are
 * sane.
 */
@NotThreadSafe
public final class JettyServerConnectorConfig {

    private final boolean tls;
    private final int listenPort;
    private final String listenHost;

    private KeyStore tlsKeystore;
    private String tlsKeystorePassphrase;
    @Nonnull
    private List<String> tlsCipherSuites = newArrayList(
            "TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384",
            "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256",
            "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384",
            "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256",
            "TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384",
            "TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256",
            "TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256",
            "TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384",
            "TLS_EMPTY_RENEGOTIATION_INFO_SCSV");

    /**
     * SSLv2Hello in default because AWS ELB's actual requests (not health checks) arrive using SSLv2 ClientHello
     */
    @Nonnull
    private List<String> tlsProtocols = newArrayList("SSLv2Hello", "TLSv1.2");

    public JettyServerConnectorConfig(@Nonnull String listenHost, int listenPort, boolean tls) {
        this.tls = tls;
        this.listenPort = listenPort;
        this.listenHost = checkNotNull(listenHost);
    }

    public static JettyServerConnectorConfig forHttp(@Nonnull String host, int port) {
        return new JettyServerConnectorConfig(host, port, false);
    }

    public static JettyServerConnectorConfig forHttps(@Nonnull String host, int port) {
        return new JettyServerConnectorConfig(host, port, true);
    }

    public boolean isTls() {
        return tls;
    }

    public int getListenPort() {
        return listenPort;
    }

    @Nonnull
    public String getListenHost() {
        return listenHost;
    }

    @Nonnull
    public List<String> getTlsCipherSuites() {
        return tlsCipherSuites;
    }

    /**
     * @param tlsCipherSuites List of TLS cipher suites, passed to {@link SslContextFactory#setIncludeCipherSuites(String...)}
     */
    public void setTlsCipherSuites(@Nonnull List<String> tlsCipherSuites) {
        assertTls();
        this.tlsCipherSuites = checkNotNull(tlsCipherSuites);
    }

    @Nonnull
    public List<String> getTlsProtocols() {
        return tlsProtocols;
    }

    /**
     * @param tlsProtocols List of TLS protocols, passed to {@link SslContextFactory#setIncludeProtocols(String...)}
     */
    public void setTlsProtocols(@Nonnull List<String> tlsProtocols) {
        assertTls();
        this.tlsProtocols = checkNotNull(tlsProtocols);
    }

    public KeyStore getTlsKeystore() {
        return tlsKeystore;
    }

    /**
     * @param tlsKeystore Keystore to use for TLS private keys
     */
    public void setTlsKeystore(@Nonnull KeyStore tlsKeystore) {
        assertTls();
        this.tlsKeystore = checkNotNull(tlsKeystore);
    }

    public String getTlsKeystorePassphrase() {
        return tlsKeystorePassphrase;
    }

    public void setTlsKeystorePassphrase(@Nonnull String tlsKeystorePassphrase) {
        assertTls();
        this.tlsKeystorePassphrase = checkNotNull(tlsKeystorePassphrase);
    }

    /**
     * @param tlsProtocols tls protocls
     * @return this
     * @see JettyServerConnectorConfig#setTlsProtocols(List)
     */
    @Nonnull
    public JettyServerConnectorConfig withTlsProtocols(@Nonnull List<String> tlsProtocols) {
        setTlsProtocols(tlsProtocols);
        return this;
    }

    /**
     * @param tlsCipherSuites tls cipher suites
     * @return this
     * @see JettyServerConnectorConfig#setTlsCipherSuites(List)
     */
    @Nonnull
    public JettyServerConnectorConfig withTlsCipherSuites(@Nonnull List<String> tlsCipherSuites) {
        setTlsCipherSuites(tlsCipherSuites);
        return this;
    }

    /**
     * @param tlsKeystorePassphrase tls keystore passphrase
     * @return this
     * @see JettyServerConnectorConfig#setTlsKeystorePassphrase(String)
     */
    @Nonnull
    public JettyServerConnectorConfig withTlsKeystorePassphrase(@Nonnull String tlsKeystorePassphrase) {
        setTlsKeystorePassphrase(tlsKeystorePassphrase);
        return this;
    }

    /**
     * @param tlsKeystore tls keystore
     * @return this
     * @see JettyServerConnectorConfig#setTlsKeystore(KeyStore)
     */
    @Nonnull
    public JettyServerConnectorConfig withTlsKeystore(@Nonnull KeyStore tlsKeystore) {
        setTlsKeystore(tlsKeystore);
        return this;
    }

    /**
     * @throws IllegalStateException if tls is false
     */
    private void assertTls() {
        if (!tls) {
            throw new IllegalStateException("Only applicable to TLS connectors");
        }
    }
}
