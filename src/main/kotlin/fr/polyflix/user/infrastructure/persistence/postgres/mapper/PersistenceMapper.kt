package fr.polyflix.user.infrastructure.persistence.postgres.mapper

interface PersistenceMapper<Domain, Entity> {
    /**
     * Convert a DAO entity class to a domain class
     */
    fun toDomain(entity: Entity): Domain

    /**
     * Convert a domain class to a DAO entity class
     */
    fun toEntity(domain: Domain): Entity
}