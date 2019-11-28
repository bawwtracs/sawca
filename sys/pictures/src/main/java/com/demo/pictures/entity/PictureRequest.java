package com.demo.pictures.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * <p>
 *
 * </p>
 *
 * @author hjl
 * @since 2019-11-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Entity
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class PictureRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Long timestamp;

    @Column
    private String operation;

    @Column
    private Integer sendTotal;

    @Column
    private Integer matchTotal;


}
