package no.kristiania.enterprise_exam.model

import java.time.LocalDateTime
import java.util.Date
import javax.persistence.*

@Entity
@Table(name = "deliveries")
class DeliveryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "deliveries_order_id_seq")
    @SequenceGenerator(
        name = "deliveries_order_id_seq",
        allocationSize = 1
    )
    @Column(name = "order_id")
    val orderId: Long? = null,

    @Column(name="order_date")
    val orderDate: LocalDateTime = LocalDateTime.now(),

    @Column(name="delivered_date")
    val deliveredDate: LocalDateTime? = null,

    @Column(name="is_delivered")
    val isDelivered: Boolean? = false,

    @ManyToOne
    @JoinColumn(name = "customer_id")
    val customer: CustomerEntity?,

    @OneToMany(fetch = FetchType.EAGER, cascade= [CascadeType.ALL])
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    val orderedProducts: MutableList<OrderedProductEntity> = mutableListOf()


) {
    override fun toString(): String {
        return "DeliveryEntity(orderId=$orderId, customer=$customer, orderedProducts=$orderedProducts)"
    }
}



