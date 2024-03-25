package com.invstore.invstorejvm.repositories.catering

import com.invstore.invstorejvm.models.catering.CateringItem
import org.springframework.data.jpa.repository.JpaRepository

interface CateringItemRepository : JpaRepository<CateringItem, Long>