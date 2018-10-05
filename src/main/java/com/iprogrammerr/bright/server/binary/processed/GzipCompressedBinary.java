package com.iprogrammerr.bright.server.binary.processed;

import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPOutputStream;

public final class GzipCompressedBinary implements CompressedBinary {

    private final byte[] source;

    public GzipCompressedBinary(byte[] source) {
	this.source = source;
    }

    @Override
    public byte[] content() throws Exception {
	ByteArrayOutputStream baos = new ByteArrayOutputStream(this.source.length);
	GZIPOutputStream gzos = new GZIPOutputStream(baos);
	gzos.write(this.source);
	gzos.close();
	return baos.toByteArray();
    }

    @Override
    public String algorithm() {
	return "gzip";
    }

}
