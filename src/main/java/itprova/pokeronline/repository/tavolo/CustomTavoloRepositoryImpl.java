package itprova.pokeronline.repository.tavolo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;

import itprova.pokeronline.model.Tavolo;
import itprova.pokeronline.model.Utente;

public class CustomTavoloRepositoryImpl implements CustomTavoloRepository {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<Tavolo> findByExample(Tavolo example, Utente utente) {
		Map<String, Object> paramaterMap = new HashMap<String, Object>();
		List<String> whereClauses = new ArrayList<String>();

		StringBuilder queryBuilder = new StringBuilder("select t from Tavolo t where t.id = t.id ");

		if (utente != null) {
			whereClauses.add("t.utenteCreazione.id = :utenteCrezione.id");
			paramaterMap.put("utenteCrezione", utente);
		}

		if (StringUtils.isNotEmpty(example.getDenominazione())) {
			whereClauses.add(" t.denominazione  like :denominazione ");
			paramaterMap.put("denominazione", "%" + example.getDenominazione() + "%");
		}
		if (example.getCifraMinima() != null) {
			whereClauses.add(" t.cifraMinima = :cifraMinima ");
			paramaterMap.put("cifraMinima", example.getCifraMinima());
		}
		if (example.getEsperienzaMin() != null) {
			whereClauses.add(" t.esperienzaMin = :esperienzaMin ");
			paramaterMap.put("esperienzaMin", example.getEsperienzaMin());
		}
		if (example.getDataCreazione() != null) {
			whereClauses.add(" t.dataCreazione = :dataCreazione ");
			paramaterMap.put("dataCreazione", example.getDataCreazione());
		}
		if (example.getUtenteCreazione() != null) {
			whereClauses.add(" t.utenteCreazione.id :utenteCreazione.id ");
			paramaterMap.put("utenteCreazione", example.getDataCreazione());
		}
		queryBuilder.append(!whereClauses.isEmpty() ? " and " : "");
		queryBuilder.append(StringUtils.join(whereClauses, " and "));
		TypedQuery<Tavolo> typedQuery = entityManager.createQuery(queryBuilder.toString(), Tavolo.class);

		for (String key : paramaterMap.keySet()) {
			typedQuery.setParameter(key, paramaterMap.get(key));
		}

		return typedQuery.getResultList();
	}
}
