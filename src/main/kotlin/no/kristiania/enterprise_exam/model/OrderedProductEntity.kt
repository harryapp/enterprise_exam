package no.kristiania.enterprise_exam.model

import javax.persistence.*

@Entity
@Table(name = "ordered_products")
class OrderedProductEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ordered_products_ordered_product_id_seq")
    @SequenceGenerator(
        name = "ordered_products_ordered_product_id_seq",
        allocationSize = 1
    )
    @Column(name="ordered_product_id")
    val orderedProductId: Long? = null,

    @ManyToOne
    @JoinColumn(name = "product_id")
    val product: ProductEntity?,

    val quantity: Int?
) {


}




