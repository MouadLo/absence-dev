import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IMatiere } from 'app/shared/model/matiere.model';
import { getEntities as getMatieres } from 'app/entities/matiere/matiere.reducer';
import { getEntity, updateEntity, createEntity, reset } from './horaire.reducer';
import { IHoraire } from 'app/shared/model/horaire.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IHoraireUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const HoraireUpdate = (props: IHoraireUpdateProps) => {
  const [matiereId, setMatiereId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { horaireEntity, matieres, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/horaire');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getMatieres();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...horaireEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="intranetApp.horaire.home.createOrEditLabel">
            <Translate contentKey="intranetApp.horaire.home.createOrEditLabel">Create or edit a Horaire</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : horaireEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="horaire-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="horaire-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="idHoraireLabel" for="horaire-idHoraire">
                  <Translate contentKey="intranetApp.horaire.idHoraire">Id Horaire</Translate>
                </Label>
                <AvField
                  id="horaire-idHoraire"
                  type="string"
                  className="form-control"
                  name="idHoraire"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="heureDepartLabel" for="horaire-heureDepart">
                  <Translate contentKey="intranetApp.horaire.heureDepart">Heure Depart</Translate>
                </Label>
                <AvField
                  id="horaire-heureDepart"
                  type="text"
                  name="heureDepart"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="heureFLabel" for="horaire-heureF">
                  <Translate contentKey="intranetApp.horaire.heureF">Heure F</Translate>
                </Label>
                <AvField
                  id="horaire-heureF"
                  type="text"
                  name="heureF"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="dateLabel" for="horaire-date">
                  <Translate contentKey="intranetApp.horaire.date">Date</Translate>
                </Label>
                <AvField
                  id="horaire-date"
                  type="date"
                  className="form-control"
                  name="date"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/horaire" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  matieres: storeState.matiere.entities,
  horaireEntity: storeState.horaire.entity,
  loading: storeState.horaire.loading,
  updating: storeState.horaire.updating,
  updateSuccess: storeState.horaire.updateSuccess,
});

const mapDispatchToProps = {
  getMatieres,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(HoraireUpdate);
