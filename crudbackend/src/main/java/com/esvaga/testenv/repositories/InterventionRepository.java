package com.esvaga.testenv.repositories;

import com.esvaga.testenv.interfaces.repositories.IInterventionRepository;
import com.esvaga.testenv.model.Intervention;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InterventionRepository implements IInterventionRepository<Intervention, String> {

    private final String _SQL = "SELECT int.UC_INTERVENTION_ID, NVL(op.NAME, int.CREATED_BY) created_by,bcs.DESCRIPTION intervention_status, "+
            " TO_CHAR(FROM_TZ(CAST(int.CREATED_ON AS TIMESTAMP), 'UTC') AT TIME ZONE 'EUROPE/PARIS','YYYY-MM-DD HH24:MI:SS') created_on, "+
            " clv.REGISTRATION_NUMBER vehicle_reg_number, clv.CHASSIS_NUMBER,clv.CBK_VEHICLE_MAKE_ID,clv.MODEL,int.GEO_LOCATION_INPUT,ps.NAME product_service_name,"+
            " ps.DESCRIPTION product_service_description, int_serv.CBK_STATUS_ID,assoc.NAME associate_name , acat.NAME associate_type, "+
            " LISTAGG(acac.NAME  , '; ') WITHIN GROUP (ORDER BY 1) associate_categories,executor.FIRST_NAME || ' ' || executor.LAST_NAME AS executor_full_name, "+
            " intc.NAME contact_person_name, intc.PHONE_NUMBER contact_person_phone_number, "+
            " mem.IDENTIFIER,pack.NAME product_name, ssc.NAME sales_partner_name,cbkss.NAME service_status,int.CALL_CENTER_CBK_COUNTRY_ID, "+
            " TO_CHAR(iawo.TOTAL_AMOUNT,'9,999,990.00') asc_wo_amount, awostat.NAME asc_wo_status,  awswopl.NAME asc_wo_price_list, "+
            " TO_CHAR(icwo.TOTAL_AMOUNT,'9,999,990.00') cli_wo_amount, cwostat.NAME cli_wo_status,  cwswopl.NAME cli_wo_price_list "+
            " FROM INT_UC_INTERVENTION int " +
            " LEFT JOIN BPM_CBK_STATUS bcs ON bcs.CBK_STATUS_ID  = int.CBK_STATUS_ID AND bcs.DELETED = 0 "+
            "   LEFT OUTER JOIN INT_UC_INTERVENTION_CONTACT intc ON intc.UC_INTERVENTION_ID = int.UC_INTERVENTION_ID AND intc.DELETED = 0 "+
            "       LEFT OUTER JOIN INT_UC_SERVICE int_serv ON int.UC_INTERVENTION_ID = int_serv.UC_INTERVENTION_ID "+
            "           LEFT JOIN BPM_CBK_STATUS cbkss ON cbkss.CBK_STATUS_ID = int_serv.CBK_STATUS_ID AND cbkss.DELETED = 0 "+
            " JOIN INT_UC_CLIENT_VEHICLE clv ON  int.UC_INTERVENTION_ID = clv.UC_INTERVENTION_ID "+
            " LEFT OUTER JOIN ASC_EXECUTOR executor ON INT_SERV.EXECUTOR_ID = executor.EXECUTOR_ID "+
            " LEFT OUTER JOIN ASC_ASSOCIATE assoc ON assoc.ASSOCIATE_ID = INT_SERV.ASSOCIATE_ID AND assoc.DELETED = 0"+
            " LEFT OUTER JOIN ASC_CBK_ASSOCIATE_TYPE acat ON acat.CBK_ASSOCIATE_TYPE_ID = assoc.CBK_ASSOCIATE_TYPE_ID AND acat.DELETED = 0 "+
            " LEFT OUTER JOIN ASC_ASSOCIATE_ASSOCIATE_CATEGORY aaac ON aaac.ASSOCIATE_ID = assoc.ASSOCIATE_ID AND aaac.DELETED = 0"+
            " LEFT OUTER JOIN ASC_CBK_ASSOCIATE_CATEGORY acac ON acac.CBK_ASSOCIATE_CATEGORY_ID = aaac.CBK_ASSOCIATE_CATEGORY_ID  AND aaac.DELETED = 0 "+
            "   LEFT OUTER JOIN INT_UC_ASSOCIATE_WORK_ORDER iawo ON iawo.UC_INTERVENTION_ID = int.UC_INTERVENTION_ID AND iawo.DELETED = 0 "+
            "       LEFT OUTER JOIN BPM_CBK_STATUS awostat ON awostat.CBK_STATUS_ID = iawo.CBK_STATUS_ID AND awostat.DELETED = 0 "+
            "            LEFT OUTER JOIN PRG_PRICE_LIST awswopl ON awswopl.PRICE_LIST_ID = iawo.PRICE_LIST_ID AND awswopl.DELETED = 0 " +
            "   LEFT OUTER JOIN INT_UC_CLIENT_ORDER icwo ON icwo.UC_INTERVENTION_ID = int.UC_INTERVENTION_ID AND icwo.DELETED = 0 "+
            "       LEFT OUTER JOIN BPM_CBK_STATUS cwostat ON cwostat.CBK_STATUS_ID = icwo.CBK_STATUS_ID AND cwostat.DELETED = 0 "+
            "            LEFT OUTER JOIN PRG_PRICE_LIST cwswopl ON cwswopl.PRICE_LIST_ID = icwo.PRICE_LIST_ID AND cwswopl.DELETED = 0 " +
            " JOIN PCG_PRODUCT_SERVICE pps ON pps.PRODUCT_SERVICE_ID = int_serv.PRODUCT_SERVICE_ID AND pps.DELETED = 0 "+
            "   JOIN PCG_SERVICE ps ON ps.SERVICE_ID = pps.SERVICE_ID AND ps.DELETED = 0 "+
            " LEFT OUTER JOIN MEM_MEMBERSHIP mem ON int.MEMBERSHIP_ID = mem.MEMBERSHIP_ID AND mem.DELETED = 0 "+
            "   LEFT OUTER JOIN SLS_PACKAGE pack ON pack.PACKAGE_ID = int.PACKAGE_ID AND pack.DELETED = 0 "+
            "       LEFT OUTER JOIN SLS_SALES_CHANNEL ssc ON ssc.SALES_CHANNEL_ID = int.SALES_CHANNEL_ID AND ssc.DELETED = 0 "+
            " LEFT OUTER JOIN MDM_OPERATOR op ON op.USERNAME = int.CREATED_BY AND op.DELETED = 0 "+
            " WHERE   INT_SERV.DELETED = 0 AND clv.DELETED = 0 AND EXECUTOR.DELETED = 0 "+
            " GROUP BY int.uc_intervention_id, int.created_on, "+
            " int.created_by, int.cbk_status_id, clv.registration_number, "+
            " clv.chassis_number, clv.cbk_vehicle_make_id, clv.model, int.geo_location_input, "+
            " ps.name, ps.description, int_serv.cbk_status_id, assoc.name, assoc.CBK_ASSOCIATE_TYPE_ID, "+
            " executor.first_name || ' ' || executor.last_name, "+
            " int.contact_person_name, int.contact_person_phone_number,mem.identifier, pack.name, ssc.name, int.call_center_cbk_country_id, "+
            " bcs.description ,acat.name, cbkss.name,awostat.name,awswopl.name,icwo.total_amount,cwostat.name,cwswopl.name,iawo.total_amount,"+
            " op.name, intc.name, intc.phone_number ORDER BY 1 DESC";


    private final NamedParameterJdbcTemplate njdbc;

    public InterventionRepository(@Qualifier("namedParameterJdbcTemplate") NamedParameterJdbcTemplate njdbc) {
        this.njdbc = njdbc;
    }

    @Override
    public List<Intervention> getAll() {
        return njdbc.query(_SQL, BeanPropertyRowMapper.newInstance(Intervention.class));
    }

//    @Override
//    public Intervention GetByKey(String key) {
//        var filteredQuery = SQL_QUERY + "AND int.uc_intervention_id =:id  AND in\n" + GROUP_BY_AND_ORDER;
//        return njdbc.queryForObject(filteredQuery, new);
//    }
}
