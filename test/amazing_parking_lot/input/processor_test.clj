(ns amazing-parking-lot.input.processor-test
  (:require [clojure.test :refer :all]
            [amazing-parking-lot.input.command-argument-option :refer :all])
  (:require [amazing-parking-lot.input.processor :refer [process]]
            [amazing-parking-lot.input.command-argument-option :as option]))

(deftest process-test
  (testing "when the input is valid"
    (testing "it calls the parser, validator, executor and then returns the result"
      (let [parser-called? (atom false)
            validator-called? (atom false)
            expected-message "Success"
            input-parking-lot (atom :parking-lot)
            parsed-option (create-valid-option "park" ["White" "KA-12"])
            executor-called? (atom false)
            expected-result-option (create-valid-option parsed-option expected-message input-parking-lot)
            input "some-command 6"]
        (with-redefs [amazing-parking-lot.input.parser/parse (fn [_] (reset! parser-called? true)
                                                       parsed-option)
                      amazing-parking-lot.command.validator/validate (fn [input-option] (reset! validator-called? true)
                                                                       input-option)
                      amazing-parking-lot.command.executor/execute (fn [option]
                                                                     (if (option/parking-lot option)
                                                                       (do (reset! executor-called? true)
                                                                           expected-result-option)
                                                                       (create-invalid-option "No Parking Lot")))]
          (let [result-option (process input input-parking-lot)]
            (is (= expected-message (message result-option)))
            (is (= (parking-lot result-option) input-parking-lot)))
          (is (true? (and @parser-called? @validator-called? @executor-called?)))))))
  (testing "when the input cannot be parsed"
    (testing "it short circuits after the parser and returns the result"
      (let [validator-called? (atom false)
            executor-called? (atom false)
            expected-result "Input is empty"]
        (with-redefs [amazing-parking-lot.command.validator/validate (fn [_] (reset! validator-called? true))
                      amazing-parking-lot.command.executor/execute (fn [_] (reset! executor-called? true))]
          (is (= (message (process nil nil)) expected-result))
          (is (false? (and @validator-called? @executor-called?))))))))
