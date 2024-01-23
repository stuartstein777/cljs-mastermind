(ns exfn.events
  (:require [re-frame.core :as rf]
            [exfn.logic :as bf]
            [clojure.set :as set]
            [exfn.logic :as lgc]))

(rf/reg-event-db
 :initialize
 (fn [_ _]
   {:solution             (bf/generate-solution 4)
    :current-guess-number 1
    :current-guess        {1 nil, 2 nil, 3 nil, 4 nil}
    :guesses              []
    :clues                []}))

(defn already-guessed [guesses color]
  (or (= (guesses 1) color)
      (= (guesses 2) color)
      (= (guesses 3) color)
      (= (guesses 4) color)))

(defn add-guess [current-guess color]
  (if (current-guess 1)
    (if (current-guess 2)
      (if (current-guess 3)
        (if (current-guess 4)
          current-guess
          (assoc current-guess 4 color))
        (assoc current-guess 3 color))
      (assoc current-guess 2 color))
    (assoc current-guess 1 color)))

(rf/reg-event-db
 :add-guess
 (fn [db [_ color]]
   (if (or (db :game-won?)
           (already-guessed (db :current-guess) color))
     db
     (assoc db :current-guess (add-guess (db :current-guess) color)))))

(rf/reg-event-db
 :remove-guess
 (fn [db [_ position]]
   (update db :current-guess assoc position nil)))

(rf/reg-event-db
 :check-guess
 (fn [db _]
   (let [solution (db :solution)
         guess    (db :current-guess)
         clue     (lgc/check-guess solution (vals guess))]
     (-> db
         (assoc :clues (conj (db :clues) clue))
         (update :current-guess-number inc)
         (update :guesses conj guess)
         (assoc :current-guess {1 nil, 2 nil, 3 nil, 4 nil})
         (assoc :game-won? (set/subset? (set solution) (set (vals guess))))))))

(comment
  
  (let [guess {1 nil, 2 nil, 3 nil, 4 nil}]
    (->> guess
         vals
         (remove nil?)
         count)
    )
  )