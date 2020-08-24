object RecordsServices {
    def delete(record_id: Int, user_id: Int) = {
        RecordsDAO.delete(record_id, user_id)
    }

    def restore(record_id: Int, user_id: Int) = {
        RecordsDAO.restore(record_id, user_id)
    }
}
