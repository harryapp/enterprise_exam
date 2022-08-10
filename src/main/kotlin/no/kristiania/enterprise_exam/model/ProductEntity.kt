package no.kristiania.enterprise_exam.model

import javax.persistence.*

@Entity
@Table(name = "products")
class ProductEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id", nullable = false)
    var id: Long,

    @Column(name = "product_name")
    val name: String? = null

    ) {


}
