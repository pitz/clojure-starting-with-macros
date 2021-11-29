(ns macrosproject.core
  (:gen-class))

(def order-details-validations
    {:name
     ["Por favor, informe o seu nome." not-empty]
    
     :email
     ["Por favor, informe o seu e-mail." not-empty
      "O e-mail informado não é válido."
      #(or (empty? %) (re-seq #"@" %))]})

(defn error-messages-for
    "Return a seq of erros messages"
    [to-validate message-validator-pairs]
    (map first (filter #(not ((second %) to-validate))
                        (partition 2 message-validator-pairs))))

(defn validate
    [to-validate validations]
    (reduce (fn [errors validation]
                (let [[fieldname validation-check-groups] validation
                      value (get to-validate fieldname)
                      error-messages (error-messages-for value validation-check-groups)]
                (if (empty? error-messages)
                    errors
                    (assoc errors fieldname error-messages))))
            {}
            validations))



(defn -main [& args]
  (println "Validando ->")
  
  (def order-details
      {:name "Eduardo Pitz"
       :email "eduardo.pitz@nubank.com.br"})
  
  (def order-ditails
      {:name "Eduardo Pix"
       :email "eduardo.pixnubank.com.br"})

  (println (validate order-details order-details-validations))
  (println (validate order-ditails order-details-validations))
)
