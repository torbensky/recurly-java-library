/*
 * Copyright 2010-2012 Ning, Inc.
 * Copyright 2013 Torben Werner - Modifications to support new Coupon fields
 *
 * Ning licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.github.torbinsky.billing.recurly.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Class that represents the Concept of a Coupon within the Recurly API.
 */
@XmlRootElement(name = "coupon")
public class Coupon extends RecurlyObject {

	@XmlTransient
	public static final String COUPON_RESOURCE = "/coupons";
	
	@XmlTransient
    public static final Pattern COUPON_CODE_PATTERN = Pattern.compile(COUPON_RESOURCE + "/(.+)$");
	
	@XmlTransient
    private String href;

	@XmlElement(name = "name")
	private String name;

	@XmlElement(name = "coupon_code")
	private String couponCode;

	@XmlElement(name = "discount_type")
	private String discountType;

	@XmlElement(name = "discount_percent")
	private Integer discountPercent;

	@XmlElement(name = "applies_for_months")
	private Integer appliesForMonths;

	/**
	 * Gets the name of the {@link Coupon}
	 * 
	 * @return The {@link Coupon} name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the {@link Coupon}
	 * 
	 * @param name
	 *            The Name that is to be given to the {@link Coupon}
	 */
	public void setName(final Object name) {
		this.name = stringOrNull(name);
	}

	/**
	 * Gets the coupon code for a {@link Coupon}
	 * 
	 * @return The coupon code for the {@link Coupon}
	 */
	public String getCouponCode() {
		return couponCode;
	}

	/**
	 * Sets the coupon code for the {@link Coupon}
	 * 
	 * @param couponCode
	 *            The coupon code
	 */
	public void setCouponCode(final Object couponCode) {
		this.couponCode = stringOrNull(couponCode);
	}
	
	// Note: I'm not sure why @JsonIgnore is required here - shouldn't @XmlTransient be enough?
    @JsonIgnore
    public String getHref() {
        return href;
    }

    public void setHref(final Object href) {
        this.href = stringOrNull(href);

        // If there was an href try to parse out the account code since
        // Recurly doesn't currently provide it elsewhere.
        if (this.href != null) {
            Matcher m = COUPON_CODE_PATTERN.matcher(this.href);
            if (m.find()) {
                setCouponCode(m.group(1));
            }
        }
    }

	/**
	 * Sets the discount type for a {@link Coupon}
	 * 
	 * @param discountType
	 *            A String of: 'percent'; 'dollars';
	 */
	public void setDiscountType(final Object discountType) {
		this.discountType = stringOrNull(discountType);
	}

	/**
	 * Gets the discount type associated with the {@link Coupon}
	 * 
	 * @return A String defining the discount type: 'percent' or 'dollars'.
	 */
	public String getDiscountType() {
		return discountType;
	}

	/**
	 * Gets the percentage discount for a coupon
	 * 
	 * @return The percentage
	 */
	public Integer getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(final Object discountPercent) {
		this.discountPercent = integerOrNull(discountPercent);
	}

	/**
	 * 
	 * 
	 * @return the number of months the coupon applies for
	 */
	public Integer getAppliesForMonths() {
		return appliesForMonths;
	}

	public void setAppliesForMonths(final Object appliesForMonths) {
		this.appliesForMonths = integerOrNull(appliesForMonths);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("Coupon");
		sb.append("{name='").append(name).append('\'');
		sb.append(", href='").append(href).append('\'');
		sb.append(", couponCode='").append(couponCode).append('\'');
		sb.append(", discountType='").append(discountType).append('\'');
		sb.append(", discountPercent='").append(discountPercent).append('\'');
		sb.append(", appliesForMonths='").append(appliesForMonths).append('\'');
		sb.append('}');
		return sb.toString();
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		final Coupon coupon = (Coupon) o;

		if (couponCode != null ? !couponCode.equals(coupon.couponCode) : coupon.couponCode != null) {
			return false;
		}
		if (discountPercent != null ? !discountPercent.equals(coupon.discountPercent) : coupon.discountPercent != null) {
			return false;
		}
		if (discountType != null ? !discountType.equals(coupon.discountType) : coupon.discountType != null) {
			return false;
		}
		if (name != null ? !name.equals(coupon.name) : coupon.name != null) {
			return false;
		}
		if (href != null ? !href.equals(coupon.href) : coupon.href != null) {
			return false;
		}
		
		if (appliesForMonths != null ? !appliesForMonths.equals(coupon.appliesForMonths) : coupon.appliesForMonths != null) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + (href != null ? href.hashCode() : 0);
		result = 31 * result + (couponCode != null ? couponCode.hashCode() : 0);
		result = 31 * result + (discountType != null ? discountType.hashCode() : 0);
		result = 31 * result + (discountPercent != null ? discountPercent.hashCode() : 0);
		result = 31 * result + (appliesForMonths != null ? appliesForMonths.hashCode() : 0);
		return result;
	}
}
