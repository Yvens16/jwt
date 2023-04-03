SELECT *
  FROM transaction
  INNER JOIN user_entity on transaction.buyer_id=user_entity.id;