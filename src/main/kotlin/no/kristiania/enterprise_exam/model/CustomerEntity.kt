package no.kristiania.enterprise_exam.model

import javax.persistence.*

@Entity
@Table(name = "customers")
class CustomerEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "customer_id")
    val id: Long? = null,

    @Column(name = "customer_email")
    val email: String? = null,

    @Column(name = "customer_phone")
    val phone: String?  = null,

    @ManyToOne
    @JoinColumn(name = "address_id")
    val address: AddressEntity,
) {


}