package fr.polyflix.user.infrastructure.authentication.keycloak.event

import fr.polyflix.user.infrastructure.authentication.keycloak.model.KeycloakUser

data class KeycloakUserEvent(val trigger: KeycloakEventType, val data: KeycloakUser)