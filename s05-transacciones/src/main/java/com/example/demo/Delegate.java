package com.example.demo;
import com.example.demo.api.TransactionsApiDelegate;
import com.example.demo.document.TransactionEntity;
import com.example.demo.mapper.TransactionMapper;
import com.example.demo.model.SummaryResponse;
import com.example.demo.model.Transaction;
import com.example.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Delegate implements TransactionsApiDelegate {
    /**
     *  Llamada al TransactionService.
     */
    @Autowired
    private TransactionService transactionService;

    /**
     * Método para transacción de cliente.
     * @param clientId busqueda de transaccion.
     * @return Retorna apiModelTransactions.
     */
    @Override
    public ResponseEntity<List<Transaction>> transactionsClientClientIdGet(final String clientId) {
        List<TransactionEntity> entityTransactions = transactionService.getTransactionsByClientId(clientId);

        List<Transaction> apiModelTransactions = entityTransactions.stream()
                .map(TransactionMapper::toModel)
                .collect(Collectors.toList());

        if (!apiModelTransactions.isEmpty()) {
            return ResponseEntity.ok(apiModelTransactions);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Método para transacción.
     * @param apiModelTransaction busqueda de transaccion.
     * @return Retorna apiModelTransactions.
     */
    @Override
    public ResponseEntity<Transaction> transactionsPost(final Transaction apiModelTransaction) {
        TransactionEntity entityTransaction = TransactionMapper.toEntity(apiModelTransaction);

        TransactionEntity savedEntityTransaction = transactionService.saveTransaction(entityTransaction);

        Transaction savedApiModelTransaction = TransactionMapper.toModel(savedEntityTransaction);

        if (savedApiModelTransaction != null) {
            return ResponseEntity.ok(savedApiModelTransaction);
        } else {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    /**
     * Método para SummaryResponse.
     * @param clientId variable.
     * @return TransactionsApiDelegate de cliente.
     */
    @Override
    public ResponseEntity<SummaryResponse> transactionsSummaryDailyClientIdGet(final String clientId) {
        return TransactionsApiDelegate.super.transactionsSummaryDailyClientIdGet(clientId);
    }
}

