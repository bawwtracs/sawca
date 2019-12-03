package com.demo.pictures.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

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
public class RequestImage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Long requestId;

    @Column
    private String name;

    @Column
    private String path;

    @Column
    private Long requestTime;

    @Column
    private Long responseTime;

    @Column
    private String operation;

    @Column
    private String type;

    @Column(columnDefinition = "text")
    private String result;
}
