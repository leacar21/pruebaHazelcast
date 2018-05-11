package com.despegar.example.hazelcast.job.process;

import java.io.Serializable;

public class InputJobDTO
    implements Serializable {

	private static final long serialVersionUID = 1L;
	private String productType;

    public InputJobDTO() {
    }

    public InputJobDTO(String productType) {
        this.productType = productType;
    }

    public String getProductType() {
        return this.productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getKey() {
        return this.productType;
    }
}
