package com.example.demo.service;
import com.example.demo.document.TransactionEntity;
import com.example.demo.mapper.TransactionMapper;
import com.example.demo.model.AccountDetails;
import com.example.demo.model.Summary;
import com.example.demo.model.SummaryResponse;
import com.example.demo.model.Transaction;
import com.example.demo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    /**
     * Repositorio para acceder a la BD de TransactionRepository.
     */
    @Autowired
    private TransactionRepository transactionRepository;

    /**
     * Método para guardar una transacción.
     *
     * @param transaction El objeto transacción (debe ser final).
     * @return Retorna la transacción guardada.
     */
    public TransactionEntity saveTransaction(final TransactionEntity transaction) {
        return transactionRepository.save(transaction).block();
    }

    /**
     * Buscar por ClientId.
     * @param clientId Variable de cliente.
     * @return RettransactionRepositoryorna del clientId.
     */
    public List<TransactionEntity> getTransactionsByClientId(final String clientId) {
        return transactionRepository.findByClientId(clientId);
    }

    /**
     * Buscar por ClientId.
     * @param clientId Variable de cliente.
     * @return summaryResponse para SummaryResponse.
     */
    public SummaryResponse transactionsSummaryDailyClientIdGet(final String clientId) {
        LocalDateTime currentDate = LocalDateTime.now();
        int currentMonth = currentDate.getMonthValue();
        int currentYear = currentDate.getYear();
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8081/accounts/byList";
        List<String> clientIds = Arrays.asList(clientId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<String>> requestEntity = new HttpEntity<List<String>>(clientIds, headers);
        ParameterizedTypeReference<List<AccountDetails>> responseType = new ParameterizedTypeReference<List<AccountDetails>>() {
        };
        ResponseEntity<List<AccountDetails>> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                responseType
        );
        List<AccountDetails> accounts = response.getBody();
        SummaryResponse summaryResponse = new SummaryResponse();
        summaryResponse.setClientId(clientId);
        summaryResponse.setSummaries(accounts.stream().map(account -> {
            List<Transaction> currentMonthTransactions = this.getTransactionsByClientId(clientId)
                    .stream()
                    .filter(transaction -> transaction.getTransactionDate().getMonthValue() == currentMonth
                            && transaction.getTransactionDate().getYear() == currentYear)
                    .map(TransactionMapper::toModel)
                    .collect(Collectors.toList());

            Map<OffsetDateTime, Double> dailyBalances = currentMonthTransactions.stream()
                    .collect(Collectors.groupingBy(
                            transaction -> transaction.getTransactionDate(),
                            Collectors.summingDouble(Transaction::getAmount)));

            double averageDailyBalance = dailyBalances.values()
                    .stream()
                    .mapToDouble(balance -> balance)
                    .average()
                    .orElse(0.0);

            Summary summary = new Summary();
            summary.setAccountId(account.getId());
            summary.setAverageDailyBalance(averageDailyBalance);
            return summary;
        }).collect(Collectors.toList()));
        return summaryResponse;
    }
}

