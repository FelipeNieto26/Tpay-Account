package com.banreservas.tpay.account.repositories.impl;

import com.banreservas.tpay.account.dtos.TPayAccountRequest;
import com.banreservas.tpay.account.dtos.TPayAccountResponse;
import com.banreservas.tpay.account.exceptions.dto.DataBaseException;
import com.banreservas.tpay.account.repositories.ITPayRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;


@ApplicationScoped
public class TPayRepositoryImpl implements ITPayRepository, PanacheRepositoryBase<TPayAccountResponse,String> {

    static final Logger log = Logger.getLogger(TPayRepositoryImpl.class);

    @ConfigProperty(name = "tpay.account.sp.message.error.technical")
    String messageErrorTechnical;

    @ConfigProperty(name = "tpay.account.sp")
    String spQuery;

    @ConfigProperty(name = "tpay.account.sp.format.date.sp")
    String formatDateSP;

    @ConfigProperty(name = "tpay.account.format.language.sp")
    String formatLanguageSP;

    @ConfigProperty(name = "tpay.account.format.territory.sp")
    String formatTerritorySP;


    EntityManager entityManager;

    @Inject
    public TPayRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void setNlsDateFormat() {
        entityManager.createNativeQuery(formatDateSP).executeUpdate();
        entityManager.createNativeQuery(formatLanguageSP).executeUpdate();
        entityManager.createNativeQuery(formatTerritorySP).executeUpdate();
    }

    @Override
    public Uni<TPayAccountResponse> getUnmaskProduct(TPayAccountRequest tPayAccountRequest) {
        return Uni.createFrom().item(() -> {
            try {

                StoredProcedureQuery query = entityManager.createStoredProcedureQuery(spQuery);

                query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
                query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
                query.registerStoredProcedureParameter(3, String.class, ParameterMode.OUT);
                query.registerStoredProcedureParameter(4, String.class, ParameterMode.OUT);

                query.setParameter(1, tPayAccountRequest.producNumber());
                query.setParameter(2, tPayAccountRequest.producType());

                query.execute();

                return new TPayAccountResponse(query.getOutputParameterValue(3).toString(), query.getOutputParameterValue(4).toString());

            } catch (Exception e) {
                log.error(e.getMessage());
                throw new DataBaseException(messageErrorTechnical + " " + e.getMessage());
            }
        }).runSubscriptionOn(Infrastructure.getDefaultWorkerPool());
    }
}
