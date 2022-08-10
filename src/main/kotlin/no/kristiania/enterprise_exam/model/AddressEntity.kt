package no.kristiania.enterprise_exam.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "addresses")
class AddressEntity(

    @Id
    @Column(name = "address_id")
    val addressId: Long,

    @Column(name = "street")
    val street: String,

    @Column(name = "zip_code")
    val zipCode: String,

    @Column(name = "city")
    val city: String,

    @Column(name = "country")
    val country: String

) {

}