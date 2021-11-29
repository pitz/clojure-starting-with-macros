(ns macrosproject.core
  (:gen-class))

(def order-details
    {:name "Mitchard Blimmons"
     :email "mitchardgmail.com"})

(def order-details-validations
    {:name
     ["Please enter a name" not-empty]
    
     :email
     ["Please enter an email" not-empty
      "Your email address doesn't like an valida email"
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



(defn -main
  "I don't do a whole lot ... yet."
  [& args]

  (println "VALIDAÇÃO:")
  (println (validate order-details order-details-validations))
)
