package com.ztev.nbi.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by ${xiaozb} on 2017/11/24.
 *
 * @copyright by ztev
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="RegistryInfo")
public class RegistryInfo
{
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    @Id
    private long id;
    private  String operatorId;
    private  String description;
    private  String secretKey;
}
