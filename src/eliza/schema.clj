(ns eliza.schema)

(defprotocol schema-protocol
  (get-concepts-except [this handle]))

(defrecord schema [function])