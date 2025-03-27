package com.example.countries_challenge.domain.exceptions

import java.io.IOException

class NoConnectivityException(override val message: String): IOException(message)