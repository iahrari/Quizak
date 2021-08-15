package ir.dagger.quizak.db.entity.customers

import javax.persistence.Embeddable

/**
 * Address is being used to store data about
 * @author <a href="https://github.com/iahrari">Iman Ahrari</a>
 * @param latitude is the exact latitude of the address
 * @param longitude is the exact longitude of the address
 * @param city is the name of city
 * @param province is the name of province or state
 * @param country is the name of country
 * @param address is the address
 */
@Embeddable
data class Address(
    var latitude: Long,
    var longitude: Long,
    var city: String,
    var province: String,
    var country: String,
    var address: String,
)